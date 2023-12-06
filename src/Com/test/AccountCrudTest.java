package Com.test;

import models.Account;
import models.AccountType;
import repository.AccountCrudOperations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountCrudTest {
    public  static void AccountTest(){
        LocalDateTime updatedDate = LocalDateTime.now() ;
        BigDecimal value = new BigDecimal("45554441110.0") ;
        Account firstAccount = new Account("azer1244" , "General" ,value, updatedDate ,1 , AccountType.BANK ) ;
        Account secondAccount = new Account("azer12456" , "Depot" ,value , updatedDate , 2 , AccountType.CASH ) ;
        Account thirdAccount = new Account("arddkdjf1" , "my account" , value , updatedDate ,1, AccountType.BANK) ;
        AccountCrudOperations allAccount = new AccountCrudOperations();
        System.out.println("The list of all account");
        allAccount.findAll() ;
        List<Account> accounts = new ArrayList<>() ;
        accounts.add(firstAccount) ;
        accounts.add(secondAccount) ;
        List<Account> savedAccounts = allAccount.saveAll(accounts) ;
        System.out.println("Saved accounts are : ");
        for (Account savedAccount : savedAccounts){
            System.out.println(savedAccount);
        }
        allAccount.save(thirdAccount) ;
        System.out.println("Saving Account" + thirdAccount);
        firstAccount.setName("General");
        allAccount.update(firstAccount) ;
        System.out.println("Account" + firstAccount + "has been updated");

    }
}