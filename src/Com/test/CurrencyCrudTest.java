package Com.test;

import models.Currency;
import repository.CurrencyCrudOperations;

import java.util.ArrayList;
import java.util.List;

public class CurrencyCrudTest {
    public static void CurrencyTest(){
        Currency euroCurrency = new Currency(1 , "EUR" ,"EURO") ;
        Currency dollarsCurrency = new Currency( 2, "USD" ,"US Dollar") ;
        Currency ariaryCurrency = new Currency(3 , "AR" ,"ARIARY") ;
        CurrencyCrudOperations allCurrency = new CurrencyCrudOperations() ;
        System.out.println("The list of all currency : ");
        allCurrency.findAll()  ;
        allCurrency.save(ariaryCurrency) ;
        System.out.println("Saving currency : "+ ariaryCurrency);
        List<Currency> savedCurrency = new ArrayList<>() ;
        savedCurrency.add(euroCurrency) ;
        savedCurrency.add(dollarsCurrency) ;
        List<Currency> currencies = allCurrency.saveAll(savedCurrency) ;
        System.out.println("Saved currency are : ");
        for (Currency currency :currencies){
            System.out.println(currency);
        }
        euroCurrency.setName("European Euro");
        allCurrency.update(euroCurrency);
    }
}
