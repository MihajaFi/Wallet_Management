package services;

import configuration.ConnectionConfig;
import models.TransactionType;
import repository.TransactionCrudOperations;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;

public class MoneyTransfer {
    private static Connection connection;

    static {

        getConnection();
    }

    private static void getConnection() {
        ConnectionConfig connex = new ConnectionConfig();
        connection = connex.createConnection();
    }

    public static void transferMoney(String senderId, String receivedId, BigDecimal transferAmount, String senderCategoryId,String receiverCategoryId) throws SQLException {
        BigDecimal senderBalance = getAccountBalance(senderId);

        if (transferAmount.compareTo(senderBalance) > 0) {
            throw new RuntimeException("Funds insufficient");
        }
        if(isExpenseCategory(senderCategoryId)){
            insertTransaction( "Send money", transferAmount, TransactionType.DEBIT, senderId ,senderCategoryId);

        }else{
            insertTransaction( "Send money", transferAmount, TransactionType.CREDIT, senderId ,senderCategoryId);

        }
        if (isExpenseCategory(receiverCategoryId)){
            insertTransaction("Receive money", transferAmount, TransactionType.DEBIT, receivedId ,receiverCategoryId);
        }else{
            insertTransaction("Receive money", transferAmount, TransactionType.CREDIT, receivedId ,receiverCategoryId);
        }



        updateAccountBalance(senderId, senderBalance.subtract(transferAmount));

        BigDecimal receiverBalance = getAccountBalance(receivedId);
        updateAccountBalance(receivedId, receiverBalance.add(transferAmount));

        int senderTransactionId = getLatestTransactionId(senderId);
        int receiverTransactionId = getLatestTransactionId(receivedId);







        recordTransferHistory(senderTransactionId ,receiverTransactionId);


    }

    public static BigDecimal getAccountBalance(String accountId) throws SQLException {
        String sql = "SELECT balance FROM Account WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, accountId);
            return executeQueryAndGetSingleResult(preparedStatement);
        }
    }

    public static void updateAccountBalance(String accountId, BigDecimal newBalance) throws SQLException {
        String sql = "UPDATE Account SET balance = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBigDecimal(1, newBalance);
            preparedStatement.setString(2, accountId);
            preparedStatement.executeUpdate();
        }
    }

    public static void insertTransaction( String label, BigDecimal amount, TransactionType type, String accountId ,String categoryId) throws SQLException {
        String sql = "INSERT INTO Transaction ( label, amount, date, type, id_account ,id_category) VALUES (?, ?, NOW(), ?, ?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, label);
            preparedStatement.setBigDecimal(2, amount);
            preparedStatement.setObject(3, type, Types.OTHER);
            preparedStatement.setString(4, accountId);
            preparedStatement.setString(5 ,categoryId);
            preparedStatement.executeUpdate();
        }
    }

    public static int getLatestTransactionId(String accountId) throws SQLException {
        String sql = "SELECT id FROM Transaction WHERE id_account = ? ORDER BY date DESC LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, accountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    throw new RuntimeException("Nothing transaction on this  " + accountId);
                }
            }
        }
    }

    public static void recordTransferHistory(int senderTransactionId, int receiverTransactionId) throws SQLException {
        String sql = "INSERT INTO TransferHistory (sender_id, receiver_id, transfer_date) VALUES (?, ?, NOW())";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, senderTransactionId);
            preparedStatement.setInt(2, receiverTransactionId);
            preparedStatement.executeUpdate();
        }
    }

    public static void displayTransferHistoryInDateRange(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        String sql = "SELECT t1.id_account AS sender_id, t2.id_account AS receiver_id, th.transfer_date, t1.amount AS sender_amount " +
                "FROM TransferHistory th " +
                "JOIN Transaction t1 ON th.sender_id = t1.id " +
                "JOIN Transaction t2 ON th.receiver_id = t2.id " +
                "WHERE th.transfer_date BETWEEN ? AND ? " +
                "ORDER BY th.transfer_date";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, startDate);
            preparedStatement.setObject(2, endDate);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                System.out.println("History of transfer :");
                System.out.println("| Sender ID | Receiver ID | Transfer Date | Amount |");
                System.out.println("|-----------|-------------|---------------------|---------|");

                while (resultSet.next()) {
                    String senderId = resultSet.getString("sender_id");
                    String receiverId = resultSet.getString("receiver_id");
                    String transferDate = resultSet.getString("transfer_date");
                    BigDecimal senderAmount = resultSet.getBigDecimal("sender_amount");

                    System.out.printf("| %-9s | %-11s | %-19s | %-7s |\n",
                            senderId, receiverId, transferDate, senderAmount);
                }
            }
        }
    }


    private static BigDecimal executeQueryAndGetSingleResult(PreparedStatement preparedStatement) throws SQLException {
        try (var resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getBigDecimal(1);
            } else {
                throw new RuntimeException("Nothing result find");
            }
        }
    }

    private static boolean isExpenseCategory(String categoryId) {
        String sql = "SELECT is_expense FROM Category WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, categoryId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("is_expense");
                } else {
                    throw new RuntimeException("Category not found for id: " + categoryId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking category type", e);
        }
    }


}
