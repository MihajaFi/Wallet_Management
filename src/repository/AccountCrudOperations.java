package repository;

import configuration.ConnectionConfig;
import models.Account;
import models.AccountType;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountCrudOperations implements CrudOperations<Account> {

    private static Connection connection ;


    public static void getConnection(){
        ConnectionConfig connex = new ConnectionConfig() ;
        connection = connex.createConnection() ;
    }
    private Account extractAccountFromResultSet(ResultSet resultSet) throws SQLException{
        String id = resultSet.getString("id") ;
        String name = resultSet.getString("name");
        BigDecimal balance = resultSet.getBigDecimal("balance") ;
        LocalDateTime updatedDate = resultSet.getTimestamp("updatedDate").toLocalDateTime() ;
        int idCurrency = resultSet.getInt("id_currency") ;
        AccountType type = AccountType.valueOf(resultSet.getString("type"));
        return new Account(id , name , balance, updatedDate, idCurrency , type) ;
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
            String sql = "INSERT INTO Account (id, name, balance, updatedDate , id_currency , type) " +
                         "VALUES (?, ?, ?, ?,?,?) " +
                         "ON CONFLICT (id) DO NOTHING";
            getConnection();
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                for (Account account : toSave){
                    preparedStatement.setString(1 , account.getId());
                    preparedStatement.setString(2 , account.getName());
                    preparedStatement.setBigDecimal(3 , account.getBalance());
                    preparedStatement.setObject(4 , account.getUpdatedDate());
                    preparedStatement.setInt(5 , account.getIdCurrency());
                    preparedStatement.setObject(6 , account.getType() , Types.OTHER);

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
        String sql = "UPDATE Account SET name = ? , balance=? , updatedDate=? , id_currency=? , type= ? WHERE id = ?" ;
        getConnection();
        try(PreparedStatement preparedStatement =connection.prepareStatement(sql)){

            preparedStatement.setString(1 , toUpdate.getName());
            preparedStatement.setBigDecimal(2 , toUpdate.getBalance());
            preparedStatement.setObject(3 , toUpdate.getUpdatedDate());
            preparedStatement.setInt(4 , toUpdate.getIdCurrency());
            preparedStatement.setObject(5 , toUpdate.getType() ,Types.OTHER);
            preparedStatement.setString(6 , toUpdate.getId());
            preparedStatement.executeUpdate() ;
        } catch (SQLException e) {
                throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Account save(Account toSave) {
        try{
            String sql = "INSERT INTO Account (id, name, balance, updatedDate , id_currency , type) " +
                         "VALUES (?, ?, ?, ?,?,?) " +
                         "ON CONFLICT (id) DO NOTHING";

            getConnection();
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

                preparedStatement.setString(1 , toSave.getId());
                preparedStatement.setString(2 , toSave.getName());
                preparedStatement.setBigDecimal(3 , toSave.getBalance());
                preparedStatement.setObject(4 , toSave.getUpdatedDate());
                preparedStatement.setInt(5 , toSave.getIdCurrency());
                preparedStatement.setObject(6 , toSave.getType() ,Types.OTHER);
                    preparedStatement.executeUpdate();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
