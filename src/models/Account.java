package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Account {
    private String id;
    private String name;
    private BigDecimal balance;
    private LocalDateTime updatedDate ;
    private int idCurrency;
    private AccountType type ;
    private List<Transaction> transactions ;

    public Account(String id, String name, BigDecimal balance, LocalDateTime updatedDate, int idCurrency,  AccountType type) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.updatedDate = updatedDate;
        this.idCurrency = idCurrency;
        this.type = type;
       
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(int idCurrency) {
        this.idCurrency = idCurrency;
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
                ", updatedDate=" + updatedDate +
                ", idCurrency=" + idCurrency +
                ", type=" + type +
                ", transactions=" + transactions +
                '}';
    }
}
