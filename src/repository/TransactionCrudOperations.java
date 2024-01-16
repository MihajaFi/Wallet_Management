package repository;

import configuration.ConnectionConfig;
import models.Account;
import models.AccountType;
import models.Transaction;
import models.TransactionType;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionCrudOperations implements CrudOperations<Transaction> {
    private static Connection connection;

    public static void getConnection() {
        ConnectionConfig connex = new ConnectionConfig();
        connection = connex.createConnection();
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transaction";
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Transaction transaction = extractTransactionFromResultSet(resultSet);
                transactions.add(transaction);
                System.out.println(transaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    @Override
    public List<Transaction> saveAll(List<Transaction> toSave) {
        List<Transaction> savedTransactions = new ArrayList<>();
        try {
            String sql = "INSERT INTO Transaction (id, label, amount ,date,type, id_account, id_category) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                    "ON CONFLICT (id) DO NOTHING";
            getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (Transaction transaction : toSave) {
                    preparedStatement.setInt(1, transaction.getId());
                    preparedStatement.setString(2, transaction.getLabel());
                    preparedStatement.setBigDecimal(3, transaction.getAmount());
                    preparedStatement.setObject(4, transaction.getDate());
                    preparedStatement.setObject(5, transaction.getType(), Types.OTHER);
                    preparedStatement.setString(6, transaction.getIdAccount());
                    preparedStatement.setString(7, transaction.getIdCategory());
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        savedTransactions.add(transaction);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return savedTransactions;
    }

    @Override
    public Transaction update(Transaction toUpdate) {
        String sql = "UPDATE Transaction SET label = ?, amount = ?, date = ?, type = ?, id_account = ?, id_category = ? " +
                "WHERE id = ? AND (label <> ? OR amount <> ? OR date <> ? OR type <> ? OR id_account <> ? OR id_category <> ?)";
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, toUpdate.getLabel());
            preparedStatement.setBigDecimal(2, toUpdate.getAmount());
            preparedStatement.setObject(3, toUpdate.getDate());
            preparedStatement.setObject(4, toUpdate.getType(), Types.OTHER);
            preparedStatement.setString(5, toUpdate.getIdAccount());
            preparedStatement.setString(6, toUpdate.getIdCategory());
            preparedStatement.setInt(7, toUpdate.getId());
            preparedStatement.setString(8, toUpdate.getLabel());
            preparedStatement.setBigDecimal(9, toUpdate.getAmount());
            preparedStatement.setObject(10, toUpdate.getDate());
            preparedStatement.setObject(11, toUpdate.getType(), Types.OTHER);
            preparedStatement.setString(12, toUpdate.getIdAccount());
            preparedStatement.setString(13, toUpdate.getIdCategory());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Transaction save(Transaction toSave) {
        getConnection();
        try {
            String sql = "SELECT * FROM Account WHERE id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, toSave.getIdAccount());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                BigDecimal balance = resultSet.getBigDecimal("balance");
                LocalDateTime updatedDate = resultSet.getTimestamp("updatedDate").toLocalDateTime();
                int idCurrency = resultSet.getInt("id_currency");
                AccountType type = AccountType.valueOf(resultSet.getString("type"));
                Account account = new Account(id, name, balance, updatedDate, idCurrency, type);
                changeAccountAfterTransaction(account, toSave);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private Transaction extractTransactionFromResultSet(ResultSet resultSet) throws SQLException {
        int transactionId = resultSet.getInt("id");
        String description = resultSet.getString("label");
        BigDecimal amount = resultSet.getBigDecimal("amount");
        LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
        TransactionType type = TransactionType.valueOf(resultSet.getString("type"));
        String accountId = resultSet.getString("id_account");
        String categoryId = resultSet.getString("id_category");
        return new Transaction(transactionId, description, amount, date, type, accountId, categoryId);
    }

    public static void changeAccountAfterTransaction(Account account, Transaction transaction) {
        getConnection();
        switch (transaction.getType()) {
            case CREDIT:
                BigDecimal updatedBalance = account.getBalance().add(transaction.getAmount());
                try {
                    String sql = "INSERT INTO Transaction (id, label, amount ,date,type, id_account, id_category) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                            "ON CONFLICT (id) " +
                            "DO UPDATE SET amount = EXCLUDED.amount, label = EXCLUDED.label, " +
                            "type = EXCLUDED.type, date = EXCLUDED.date , id_account = EXCLUDED.id_account, id_category = EXCLUDED.id_category";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, transaction.getId());
                    preparedStatement.setString(2, transaction.getLabel());
                    preparedStatement.setBigDecimal(3, transaction.getAmount());
                    preparedStatement.setObject(4, transaction.getDate());
                    preparedStatement.setObject(5, transaction.getType(), Types.OTHER);
                    preparedStatement.setString(6, transaction.getIdAccount());
                    preparedStatement.setString(7, transaction.getIdCategory());
                    preparedStatement.executeUpdate();
                    String update = "UPDATE Account SET balance = ? WHERE id = ?";
                    PreparedStatement ps = connection.prepareStatement(update);
                    ps.setBigDecimal(1, updatedBalance);
                    ps.setString(2, account.getId());
                    ps.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case DEBIT:
                BigDecimal debitedBalance = account.getBalance().subtract(transaction.getAmount());
                try {
                    String sql = "INSERT INTO Transaction (id, label, amount ,date,type, id_account, id_category) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                            "ON CONFLICT (id) " +
                            "DO UPDATE SET amount = EXCLUDED.amount, label = EXCLUDED.label, " +
                            "type = EXCLUDED.type, date = EXCLUDED.date , id_account = EXCLUDED.id_account, id_category = EXCLUDED.id_category";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, transaction.getId());
                    preparedStatement.setString(2, transaction.getLabel());
                    preparedStatement.setBigDecimal(3, transaction.getAmount());
                    preparedStatement.setObject(4, transaction.getDate());
                    preparedStatement.setObject(5, transaction.getType(), Types.OTHER);
                    preparedStatement.setString(6, transaction.getIdAccount());
                    preparedStatement.setString(7, transaction.getIdCategory());
                    preparedStatement.executeUpdate();
                    String update = "UPDATE Account SET balance = ? WHERE id = ?";
                    PreparedStatement ps = connection.prepareStatement(update);
                    ps.setBigDecimal(1, debitedBalance);
                    ps.setString(2, account.getId());
                    ps.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
        }
    }

    public BigDecimal getCategorySumByIdAccount(String accountId, LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal restaurantSum = BigDecimal.ZERO;
        BigDecimal salarySum = BigDecimal.ZERO;

        String sql = "SELECT t.amount, c.name " +
                "FROM Transaction t " +
                "LEFT JOIN Category c ON t.id_category = c.id " +
                "WHERE t.id_account = ? AND t.date BETWEEN ? AND ?";

        getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, accountId);
            preparedStatement.setObject(2, startDate);
            preparedStatement.setObject(3, endDate);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    BigDecimal amount = resultSet.getBigDecimal("amount");
                    String categoryName = resultSet.getString("name");
                    if ("Restaurant".equals(categoryName)) {
                        restaurantSum = restaurantSum.add(amount);
                        System.out.println("Restaurant sum : " + restaurantSum);
                    } else if ("Salary".equals(categoryName)) {
                        salarySum = salarySum.add(amount);
                        System.out.println("Salary sum :" + salarySum);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return restaurantSum.add(salarySum);
    }


    public List<Transaction> findTransactionsByAccountIdAndDateRange(String accountId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transaction WHERE id_account = ? AND date BETWEEN ? AND ?";
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, accountId);
            preparedStatement.setObject(2, startDate);
            preparedStatement.setObject(3, endDate);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Transaction transaction = extractTransactionFromResultSet(resultSet);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }


}
