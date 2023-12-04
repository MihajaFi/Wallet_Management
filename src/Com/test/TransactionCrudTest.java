package Com.test;

import models.Transaction;
import models.TransactionType;
import repository.TransactionCrudOperations;

import java.util.ArrayList;
import java.util.List;

public class TransactionCrudTest {
    public static void TransactionTest(){
        Transaction transaction = new Transaction(123456 , java.sql.Date.valueOf("2022-01-01") , "Ty le vola" , 45000.0 , TransactionType.CREDIT, "azer12456") ;
        Transaction secondTransaction = new Transaction(789101 , java.sql.Date.valueOf("2022-01-04") ,"manala" , 78000.0 , TransactionType.DEBIT , "azer1244") ;
        TransactionCrudOperations allTransactions = new TransactionCrudOperations();
        System.out.println("The list of all transactions : ");
        allTransactions.findAll() ;
        List<Transaction> transactions = new ArrayList<>() ;
        transactions.add(transaction) ;
        transactions.add(secondTransaction) ;
        List<Transaction> savedTransactions = allTransactions.saveAll(transactions) ;
        System.out.println("Saved transactions : ");
        for (Transaction transaction1 : savedTransactions){
            System.out.println(savedTransactions);
        }

        transaction.setAmount(1000.0);
        System.out.println("Transactions modified :");
        allTransactions.update(transaction) ;

    }
}
