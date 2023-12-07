package Com.test;

import models.Transaction;
import models.TransactionType;
import repository.TransactionCrudOperations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionCrudTest {
    public static void TransactionTest(){
        LocalDateTime updatedDate = LocalDateTime.now() ;
        BigDecimal value = new BigDecimal("45000") ;
        Transaction transaction = new Transaction(12345678 , "Cadeaux noel" ,value , updatedDate , TransactionType.DEBIT , "12345678") ;
        TransactionCrudOperations test = new TransactionCrudOperations() ;
        test.save(transaction) ;

    }
}
