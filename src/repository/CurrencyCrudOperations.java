package repository;

import configuration.ConnectionConfig;
import models.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyCrudOperations implements CrudOperations<Currency>{

    private static Connection connection ;


    public static void getConnection(){
        ConnectionConfig connex = new ConnectionConfig() ;
        connection = connex.createConnection() ;
    }


    @Override
    public List<Currency> findAll() {
        List<Currency> currencys = new ArrayList<>();
        String sql = "SELECT * FROM Currency";
        getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
        ResultSet resultSet = preparedStatement.executeQuery()){
          while(resultSet.next()){
              Currency currency = extractCurrencyFromResultSet(resultSet) ;
              currencys.add(currency) ;
              System.out.println(currency);
          }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currencys;
    }

    @Override
    public List<Currency> saveAll(List<Currency> toSave) {
        List<Currency> savedCurrencies = new ArrayList<>() ;
        try{
            String sql = "INSERT INTO Currency (id, currencyCode , currencyName) " +
                         "VALUES (? , ? ,? ) " +
                         "ON CONFLICT (id) DO NOTHING" ;
            getConnection();
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (Currency currency : toSave){
                    preparedStatement.setInt(1 , currency.getId());
                    preparedStatement.setString(2 , currency.getCurrencyCode());
                    preparedStatement.setString(3, currency.getCurrencyName());

                    int rowsAffected = preparedStatement.executeUpdate() ;
                    if (rowsAffected > 0 ){
                        savedCurrencies.add(currency) ;
                    }
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return savedCurrencies;
    }

    @Override
    public Currency update(Currency toUpdate) {
        String sql = "UPDATE Currency SET currencyCode = ?, currencyName = ? WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, toUpdate.getCurrencyCode());
            preparedStatement.setString(2, toUpdate.getCurrencyName());
            preparedStatement.setInt(3, toUpdate.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Currency save(Currency toSave) {
        try{
            String sql = "INSERT INTO Currency (id, currencyCode, currencyName) " +
                    "VALUES (?, ?, ?) " +
                    "ON CONFLICT (id) DO NOTHING";

            getConnection();
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

                preparedStatement.setInt(1 , toSave.getId());
                preparedStatement.setString(2 , toSave.getCurrencyCode());
                preparedStatement.setString(3, toSave.getCurrencyName());
                preparedStatement.executeUpdate();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    private Currency extractCurrencyFromResultSet(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id") ;
        String currencyCode  = resultSet.getString("currencyCode");
        String currencyName  = resultSet.getString("currencyName");
        return new Currency(id , currencyCode , currencyName) ;
    }
}
