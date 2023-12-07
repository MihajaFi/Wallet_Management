package repository;

import configuration.ConnectionConfig;
import models.Account;
import models.BalanceHistory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BalanceHistoryCrudOperations {
    private static Connection connection ;


    public static void getConnection(){
        ConnectionConfig connex = new ConnectionConfig() ;
        connection = connex.createConnection() ;
    }
    public List<BalanceHistory> findBalanceHistory(String id) {
        List<BalanceHistory> balanceHistories = new ArrayList<>();
        String sql = "SELECT t.date , a.balance \n" +
                "FROM Transaction t \n" +
                "INNER JOIN Account a ON a.id = t.id_account \n" +
                "WHERE t.id_account = ? \n" +
                "ORDER BY t.date DESC";
        getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    BalanceHistory balanceHistory = extractBalanceHistoryFromResultSet(resultSet);
                    balanceHistories.add(balanceHistory);
                    System.out.println(balanceHistory);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return balanceHistories;
    }

    public BalanceHistory extractBalanceHistoryFromResultSet(ResultSet resultSet)throws SQLException {
        LocalDateTime transactionDate = resultSet.getTimestamp("date").toLocalDateTime() ;
        BigDecimal balance = resultSet.getBigDecimal("balance") ;
        return new BalanceHistory(transactionDate , balance) ;
    }
}
