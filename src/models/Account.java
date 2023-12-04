package models;

import java.util.List;

public class Account {
    private String accountId ;
    private String accountName ;
    private double balance;
    private String currencyCode;
    private List<Transaction> transactions ;

    public Account(String accountId, String accountName, double balance, String currencyCode) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.balance = balance;
        this.currencyCode = currencyCode;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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
                "accountId='" + accountId + '\'' +
                ", accountName='" + accountName + '\'' +
                ", balance=" + balance +
                ", currencyCode='" + currencyCode + '\'' +
                ", transactions=" + transactions +
                '}';
    }
}
