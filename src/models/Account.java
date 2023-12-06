package models;

import java.time.LocalDateTime;
import java.util.List;

public class Account {
    private String id;
    private String name;
    private double balance;
    private LocalDateTime updatedDate ; 
    private int id_currency;
    private int id_transaction  ;
    private AccountType type ;
    private List<Transaction> transactions ;

    public Account(String id, String name, double balance, int id_currency, int id_transaction, AccountType type, List<Transaction> transactions) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.id_currency = id_currency;
        this.id_transaction = id_transaction;
        this.type = type;
        this.transactions = transactions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getId_currency() {
        return id_currency;
    }

    public void setId_currency(int id_currency) {
        this.id_currency = id_currency;
    }

    public int getId_transaction() {
        return id_transaction;
    }

    public void setId_transaction(int id_transaction) {
        this.id_transaction = id_transaction;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", id_currency=" + id_currency +
                ", id_transaction=" + id_transaction +
                ", type=" + type +
                ", transactions=" + transactions +
                '}';
    }
}
