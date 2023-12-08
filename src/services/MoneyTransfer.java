package services;

import configuration.ConnectionConfig;
import models.TransactionType;
import repository.TransactionCrudOperations;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class MoneyTransfer {
    private static Connection connection;

    static {

        getConnection();
    }

    private static void getConnection() {
        ConnectionConfig connex = new ConnectionConfig();
        connection = connex.createConnection();
    }

    public static void transferMoney(String senderId, String receivedId, BigDecimal transferAmount) throws SQLException {
        BigDecimal senderBalance = getAccountBalance(senderId);

        if (transferAmount.compareTo(senderBalance) > 0) {
            throw new RuntimeException("Fonds insuffisants");
        }

        updateAccountBalance(senderId, senderBalance.subtract(transferAmount));

        BigDecimal receiverBalance = getAccountBalance(receivedId);
        updateAccountBalance(receivedId, receiverBalance.add(transferAmount));

        insertTransaction(5, "Envoi d'argent", transferAmount, TransactionType.DEBIT, senderId);
        insertTransaction(6, "Réception d'argent", transferAmount, TransactionType.CREDIT, receivedId);
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

    public static void insertTransaction(int id, String label, BigDecimal amount, TransactionType type, String accountId) throws SQLException {
        String sql = "INSERT INTO Transaction (id, label, amount, date, type, id_account) VALUES (?, ?, ?, NOW(), ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, label);
            preparedStatement.setBigDecimal(3, amount);
            preparedStatement.setObject(4, type, Types.OTHER);
            preparedStatement.setString(5, accountId);
            preparedStatement.executeUpdate();
        }
    }

    private static BigDecimal executeQueryAndGetSingleResult(PreparedStatement preparedStatement) throws SQLException {
        try (var resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getBigDecimal(1);
            } else {
                throw new RuntimeException("Aucun résultat trouvé");
            }
        }
    }
}
