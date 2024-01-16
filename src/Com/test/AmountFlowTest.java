package Com.test;

import repository.CustomTransactionOperations;
import repository.TransactionCrudOperations;
import services.AmountFlowMap;
import utils.AmountFlowResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AmountFlowTest {
    public static void FlowTest(){
        LocalDateTime startDate = LocalDateTime.of(2023, 12, 14, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 15, 17, 59, 59);
       AmountFlowResult result = AmountFlowMap.getAmountFlow("Fifaliana" , startDate , endDate) ;
        System.out.println("Total credit : " + result.getTotalCredit());
        System.out.println("Total debit : " + result.getTotalDebit());

    }

    public static void AmountTest() {
        TransactionCrudOperations transactionCrudOperations = new TransactionCrudOperations();
        CustomTransactionOperations customTransactionOperations = new CustomTransactionOperations(transactionCrudOperations);

        // Supposons que les dates de début et de fin soient définies comme suit
        LocalDateTime startDate = LocalDateTime.of(2023, 12, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 15, 23, 59);

        // Supposons que accountId soit une valeur valide
        String accountId = "Fifaliana";

        // Appelez la méthode getAmountFlowByAccountId
        BigDecimal[] result = customTransactionOperations.getAmountFlowByAccountId(accountId, startDate, endDate);

        // Affichez les résultats
        System.out.println("Total Credit: " + result[0]);
        System.out.println("Total Debit: " + result[1]);
    }

}
