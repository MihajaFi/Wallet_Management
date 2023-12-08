package Com.test;

import models.*;
import repository.AccountCrudOperations;
import repository.HistoryCrudOperation;
import repository.TransactionCrudOperations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionCrudTest {
    public static void TransactionTest(){
        // Test all method
        LocalDateTime updatedDate = LocalDateTime.now() ;
        BigDecimal value1 = new BigDecimal("0") ;
        Account firstAccount = new Account("Fifaliana" , "General" ,value1, updatedDate ,1 , AccountType.BANK ) ;
        AccountCrudOperations ac = new AccountCrudOperations() ;
        ac.save(firstAccount) ;

        BigDecimal value = new BigDecimal("50000") ;
        Transaction transaction = new Transaction(2, "Depot Account" ,value , updatedDate , TransactionType.CREDIT , "Fifaliana") ;
        TransactionCrudOperations test = new TransactionCrudOperations() ;
        test.save(transaction) ;

        LocalDateTime startDate = LocalDateTime.of(2023, 12, 8, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 8, 23, 59, 59);

        List<BalanceHistory> balanceHistories = HistoryCrudOperation.getBalanceHistory("Fifaliana" , startDate , endDate);
        System.out.println(balanceHistories);
    }
}
