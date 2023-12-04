package Com.test;

import models.Account;
import repository.AccountCrudOperations;

import java.util.ArrayList;
import java.util.List;

public class AccountCrudTest {
    public  static void AccountTest(){
        Account firstAccount = new Account("azer1244" , "General" ,451000.0 , "AR" ) ;
        Account secondAccount = new Account("azer12456" , "Depot" ,451000.0 , "EUR" ) ;
        Account thirdAccount = new Account("arddkdjf" , "my account" , 4545.55 , "EUR") ;
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
        firstAccount.setAccountName("General");
        allAccount.update(firstAccount) ;
        System.out.println("Account" + firstAccount + "has been updated");

    }
}