package Com.test;

import models.Transaction;
import models.TransactionType;
import repository.TransactionCrudOperations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionCrudTest {
    public static void TransactionTest(){
        LocalDateTime updatedDate = LocalDateTime.now() ;
        Transaction transaction = new Transaction(123456 , "Cadeaux noel" ,450000.0 , updatedDate , TransactionType.DEBIT , "12345dsdf") ;
        Transaction secondTransaction = new Transaction(789101 , "Depot Telma" ,451111.0,updatedDate,TransactionType.CREDIT,"azer1244") ;
        TransactionCrudOperations allTransactions = new TransactionCrudOperations();
        System.out.println("The list of all transactions : ");
        allTransactions.findAll() ;
        List<Transaction> transactions = new ArrayList<>() ;
        transactions.add(transaction) ;
        transactions.add(secondTransaction) ;
        List<Transaction> savedTransactions = allTransactions.saveAll(transactions) ;
        System.out.println("Saved transactions : ");
        for (Transaction transaction1 : savedTransactions){
            System.out.println(transaction1);
        }

        transaction.setAmount(1000.0);
        System.out.println("Transactions modified :");
        allTransactions.update(transaction) ;
        System.out.println(transaction);

    }
}
