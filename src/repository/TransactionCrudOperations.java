package repository;

import configuration.ConnectionConfig;
import models.Transaction;
import models.TransactionType;

import java.sql.*;
import java.time.LocalDateTime;
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
            String sql = "INSERT INTO Transaction (id, label, amount ,date,type, id_account) " +
                    "VALUES (?, ?, ?, ?,?,?) " +
                    "ON CONFLICT (id) DO NOTHING";
            getConnection();
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                for (Transaction transaction : toSave){
                    preparedStatement.setInt(1 , transaction.getId());
                    preparedStatement.setString(2 , transaction.getLabel());
                    preparedStatement.setDouble(3 , transaction.getAmount());
                    preparedStatement.setObject(4 , transaction.getDate());
                    preparedStatement.setObject(5 , transaction.getType() ,Types.OTHER );
                    preparedStatement.setString(6 , transaction.getIdAccount());

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
        String sql = "UPDATE Transaction SET label = ?, amount = ?, date = ?, type = ?, id_account = ? WHERE id = ? AND (label <> ? OR amount <> ? OR date <> ? OR type <> ? OR id_account <> ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1 , toUpdate.getLabel());
            preparedStatement.setDouble(2 , toUpdate.getAmount());
            preparedStatement.setObject(3 , toUpdate.getDate());
            preparedStatement.setObject(4 , toUpdate.getType() ,Types.OTHER );
            preparedStatement.setString(5 , toUpdate.getIdAccount());
            preparedStatement.setInt(6 , toUpdate.getId());
            preparedStatement.setString(7 , toUpdate.getLabel());
            preparedStatement.setDouble(8 , toUpdate.getAmount());
            preparedStatement.setObject(9 , toUpdate.getDate());
            preparedStatement.setObject(10 , toUpdate.getType() ,Types.OTHER );
            preparedStatement.setString(11 , toUpdate.getIdAccount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Transaction save(Transaction toSave) {
        try{
            String sql = "INSERT INTO Transaction (id, label, amount ,date,type, id_account) " +
                    "VALUES (?, ?, ?, ?,?,?) " +
                    "ON CONFLICT (id) DO NOTHING";
            getConnection();
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

                preparedStatement.setInt(1 , toSave.getId());
                preparedStatement.setString(2 , toSave.getLabel());
                preparedStatement.setDouble(3 , toSave.getAmount());
                preparedStatement.setObject(4 , toSave.getDate());
                preparedStatement.setObject(5 , toSave.getType() ,Types.OTHER );
                preparedStatement.setString(6 , toSave.getIdAccount());

                preparedStatement.executeUpdate();



            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null ;
    }

    private Transaction extractTransactionFromResultSet(ResultSet resultSet) throws SQLException {
        int transactionId = resultSet.getInt("id") ;
        String description  = resultSet.getString("label");
        Double amount = resultSet.getDouble("amount");
        LocalDateTime date  = resultSet.getTimestamp("date").toLocalDateTime();
        TransactionType type = TransactionType.valueOf(resultSet.getString("type"));
        String accountId = resultSet.getString("id_account") ;
        return new Transaction(transactionId ,description ,amount, date ,type,accountId) ;
    }
}
