package services;

import configuration.ConnectionConfig;
import utils.CategorySumResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CategorySum {
    private static Connection connection;

    static {

        getConnection();
    }

    private static void getConnection() {
        ConnectionConfig connex = new ConnectionConfig();
        connection = connex.createConnection();
    }

    public static CategorySumResult getCategorySum(String accountId , LocalDateTime startDate , LocalDateTime endDate){
        CategorySumResult result = new CategorySumResult() ;
        String sql = "SELECT * FROM get_category_sum(? ,? ,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1 ,accountId);
            preparedStatement.setObject(2 , startDate);
            preparedStatement.setObject(3 , endDate);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result.setRestaurant(resultSet.getBigDecimal("restaurant"));
                    result.setSalary(resultSet.getBigDecimal("salary"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  result ;
    }
}
