package repository;

import configuration.ConnectionConfig;
import models.Transaction;
import models.TransactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionCrudOperations implements CrudOperations<Transaction> {
    private static Connection connection ;


    public static void getConnection(){
        ConnectionConfig connex = new ConnectionConfig() ;
        connection = connex.createConnection() ;
    }
    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transaction" ;
        getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet =preparedStatement.executeQuery()){
            while (resultSet.next()){
                Transaction transaction = extractTransactionFromResultSet(resultSet);
                transactions.add(transaction) ;
                System.out.println(transaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    @Override
    public List<Transaction> saveAll(List<Transaction> toSave) {
        List<Transaction> savedTransactions = new ArrayList<>() ;
        try{
            String sql = "INSERT INTO Transaction (transactionId, date, description, amount ,type, accountId) " +
                    "VALUES (?, ?, ?, ?,?,?) " +
                    "ON CONFLICT (transactionId) DO NOTHING";
            getConnection();
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                for (Transaction transaction : toSave){
                    preparedStatement.setInt(1 , transaction.getTransactionId());
                    preparedStatement.setDate(2 , new  java.sql.Date(transaction.getDate().getTime()));
                    preparedStatement.setString(3 , transaction.getDescription());
                    preparedStatement.setDouble(4 , transaction.getAmount());
                    preparedStatement.setObject(5 , transaction.getType() ,Types.OTHER );
                    preparedStatement.setString(6 , transaction.getAccountId());

                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0){
                        savedTransactions.add(transaction) ;
                    }
                }

            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return savedTransactions ;
    }

    @Override
    public Transaction update(Transaction toUpdate) {
        String sql = "UPDATE Transaction SET date = ?, description = ?, amount = ?, type = ?, accountId = ? WHERE transactionId = ? AND (date <> ? OR description <> ? OR amount <> ? OR type <> ? OR accountId <> ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, toUpdate.getDate());
            preparedStatement.setString(2, toUpdate.getDescription());
            preparedStatement.setDouble(3, toUpdate.getAmount());
            preparedStatement.setObject(4, toUpdate.getType(), Types.OTHER);
            preparedStatement.setString(5, toUpdate.getAccountId());
            preparedStatement.setInt(6, toUpdate.getTransactionId());


            preparedStatement.setDate(7, toUpdate.getDate());
            preparedStatement.setString(8, toUpdate.getDescription());
            preparedStatement.setDouble(9, toUpdate.getAmount());
            preparedStatement.setObject(10, toUpdate.getType(), Types.OTHER);
            preparedStatement.setString(11, toUpdate.getAccountId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Transaction save(Transaction toSave) {
        try{
            String sql = "INSERT INTO Transaction (transactionId, date, description, amount ,type, accountId) " +
                    "VALUES (?, ?, ?, ?,?,?) " +
                    "ON CONFLICT (transactionId) DO NOTHING";
            getConnection();
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

                preparedStatement.setInt(1 , toSave.getTransactionId());
                preparedStatement.setDate(2 , new  java.sql.Date(toSave.getDate().getTime()));
                preparedStatement.setString(3 , toSave.getDescription());
                preparedStatement.setDouble(4 , toSave.getAmount());
                preparedStatement.setObject(5 , toSave.getType() ,Types.OTHER );
                preparedStatement.setString(6 , toSave.getAccountId());

                preparedStatement.executeUpdate();



            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null ;
    }

    private Transaction extractTransactionFromResultSet(ResultSet resultSet) throws SQLException {
        int transactionId = resultSet.getInt("transactionId") ;
        Date date  = resultSet.getDate("date");
        String description  = resultSet.getString("description");
        Double amount = resultSet.getDouble("amount");
        TransactionType type = TransactionType.valueOf(resultSet.getString("type"));
        String accountId = resultSet.getString("accountId") ;
        return new Transaction(transactionId , date , description , amount , type,accountId) ;
    }
}
