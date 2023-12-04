package repository;

import configuration.ConnectionConfig;
import models.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountCrudOperations implements CrudOperations<Account> {

    private static Connection connection ;


    public static void getConnection(){
        ConnectionConfig connex = new ConnectionConfig() ;
        connection = connex.createConnection() ;
    }
    private Account extractAccountFromResultSet(ResultSet resultSet) throws SQLException{
        String accountId = resultSet.getString("accountId") ;
        String accountName = resultSet.getString("accountName");
        Double balance = resultSet.getDouble("balance") ;
        String currencyCode = resultSet.getString("currencyCode") ;
        return new Account(accountId ,accountName, balance ,currencyCode) ;
    }
    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>() ;
        String sql = "SELECT * FROM Account" ;
        getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql) ;
        ResultSet resultSet =preparedStatement.executeQuery()){
            while (resultSet.next()){
                Account account= extractAccountFromResultSet(resultSet);
                accounts.add(account) ;
                System.out.println(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accounts;
    }

    @Override
    public List<Account> saveAll(List<Account> toSave) {
        List<Account> savedAccounts = new ArrayList<>() ;
        try{
            String sql = "INSERT INTO Account (accountId, accountName, balance, currencyCode) " +
                         "VALUES (?, ?, ?, ?) " +
                         "ON CONFLICT (accountId) DO NOTHING";
            getConnection();
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                for (Account account : toSave){
                    preparedStatement.setString(1 , account.getAccountId());
                    preparedStatement.setString(2 , account.getAccountName());
                    preparedStatement.setDouble(3 , account.getBalance());
                    preparedStatement.setString(4 , account.getCurrencyCode());

                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0){
                        savedAccounts.add(account) ;
                    }
                }

            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return savedAccounts ;
    }

    @Override
    public Account update(Account toUpdate) {
        String sql = "UPDATE Account SET accountName = ? , balance=? , currencyCode=? WHERE accountId = ?" ;
        getConnection();
        try(PreparedStatement preparedStatement =connection.prepareStatement(sql)){
            preparedStatement.setString(1 , toUpdate.getAccountName());
            preparedStatement.setDouble(2, toUpdate.getBalance());
            preparedStatement.setString(3 , toUpdate.getCurrencyCode());
            preparedStatement.setString(4 ,toUpdate.getAccountId());
            preparedStatement.executeUpdate() ;
        } catch (SQLException e) {
                throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Account save(Account toSave) {
        try{
            String sql = "INSERT INTO Account (accountId, accountName, balance, currencyCode) " +
                         "VALUES (?, ?, ?, ?) " +
                         "ON CONFLICT (accountId) DO NOTHING";

            getConnection();
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

                    preparedStatement.setString(1 , toSave.getAccountId());
                    preparedStatement.setString(2 , toSave.getAccountName());
                    preparedStatement.setDouble(3 , toSave.getBalance());
                    preparedStatement.setString(4 , toSave.getCurrencyCode());
                    preparedStatement.executeUpdate();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
