package models;

import java.sql.Date;

public class Transaction {
    private int transactionId;
    private Date date;
    private String description;
    private double amount;
    private TransactionType type;
    private String accountId ;

    public Transaction(int transactionId, Date date, String description, double amount, TransactionType type, String accountId) {
        this.transactionId = transactionId;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.accountId = accountId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}
