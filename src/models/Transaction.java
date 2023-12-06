package models;

import java.sql.Date;

public class Transaction {
    private int id;
    private String label;
    private double amount;
    private Date date;
    private TransactionType type;


    public Transaction(int id, String label, double amount, Date date, TransactionType type) {
        this.id = id;
        this.label = label;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", type=" + type +
                '}';
    }
}
