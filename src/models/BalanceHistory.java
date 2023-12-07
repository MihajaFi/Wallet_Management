package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BalanceHistory {
    private LocalDateTime date;
    private BigDecimal balance ;

    public BalanceHistory(LocalDateTime date, BigDecimal balance) {
        this.date = date;
        this.balance = balance;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BalanceHistory{" +
                "transactionDate=" + date +
                ", balance=" + balance +
                '}';
    }
}
