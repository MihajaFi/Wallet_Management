package repository;

import models.Transaction;
import models.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CustomTransactionOperations {
    private final TransactionCrudOperations transactionCrudOperations;

    public CustomTransactionOperations(TransactionCrudOperations transactionCrudOperations) {
        this.transactionCrudOperations = transactionCrudOperations;
    }

    public BigDecimal[] getAmountFlowByAccountId(String accountId, LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal totalCredit = BigDecimal.ZERO;
        BigDecimal totalDebit = BigDecimal.ZERO;


        List<Transaction> transactions = transactionCrudOperations.findTransactionsByAccountIdAndDateRange(accountId, startDate, endDate);


        for (Transaction transaction : transactions) {
            if (TransactionType.CREDIT.equals(transaction.getType())) {
                totalCredit = totalCredit.add(transaction.getAmount());
            } else if (TransactionType.DEBIT.equals(transaction.getType())) {
                totalDebit = totalDebit.add(transaction.getAmount());
            }
        }

        return new BigDecimal[]{totalCredit, totalDebit};
    }
}
