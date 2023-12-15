package services;

import configuration.ConnectionConfig;
import utils.AmountFlowResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AmountFlowMap {
    private static Connection connection;

    static {

        getConnection();
    }

    private static void getConnection() {
        ConnectionConfig connex = new ConnectionConfig();
        connection = connex.createConnection();
    }

    public static AmountFlowResult getAmountFlow(String accountId , LocalDateTime startDate , LocalDateTime endDate){
        AmountFlowResult amountFlowResult = new AmountFlowResult() ;
        String sql = " SELECT * FROM get_money_flow(? ,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1 ,accountId);
            preparedStatement.setObject(2 , startDate);
            preparedStatement.setObject(3 , endDate);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    amountFlowResult.setTotalCredit(resultSet.getBigDecimal("total_credit"));
                    amountFlowResult.setTotalDebit(resultSet.getBigDecimal("total_debit"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return amountFlowResult ;
    }
}
