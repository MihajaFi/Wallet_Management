package models;

import java.math.BigDecimal;
import java.sql.Date;

public class CurrencyValue {
    private BigDecimal amount ;
    private  int currencyFrom ;
    private int currencyTo ;
    private Date transferDate ;

    public CurrencyValue(BigDecimal amount, int currencyFrom, int currencyTo, Date transferDate) {
        this.amount = amount;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.transferDate = transferDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(int currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public int getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(int currencyTo) {
        this.currencyTo = currencyTo;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    @Override
    public String toString() {
        return "CurrencyValue{" +
                "amount=" + amount +
                ", currencyFrom=" + currencyFrom +
                ", currencyTo=" + currencyTo +
                ", transferDate=" + transferDate +
                '}';
    }
}
