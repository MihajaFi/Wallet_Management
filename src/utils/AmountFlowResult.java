package utils;

import java.math.BigDecimal;

public class AmountFlowResult {
    private BigDecimal totalCredit ;
    private BigDecimal totalDebit ;


    public BigDecimal getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(BigDecimal totalCredit) {
        this.totalCredit = totalCredit;
    }

    public BigDecimal getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(BigDecimal totalDebit) {
        this.totalDebit = totalDebit;
    }

    @Override
    public String toString() {
        return "AmountResult{" +
                "totalCredit=" + totalCredit +
                ", totalDebit=" + totalDebit +
                '}';
    }
}
