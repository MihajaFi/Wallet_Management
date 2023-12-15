import Com.test.AmountFlowTest;
import Com.test.CategorySumTest;
import Com.test.TransactionCrudTest;
import services.MoneyTransfer;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws SQLException {

        BigDecimal value1 = new BigDecimal("2400") ;

        MoneyTransfer.transferMoney("Fifaliana" , "Koto" ,value1 ,"Cat1", "Cat3" );


        int senderTransactionId = MoneyTransfer.getLatestTransactionId("Fifaliana") ;
        int receiverTransactionId = MoneyTransfer.getLatestTransactionId("Koto");
        MoneyTransfer.recordTransferHistory(senderTransactionId , receiverTransactionId);
        LocalDateTime startDate = LocalDateTime.of(2023, 12, 14, 17, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 14, 17, 59, 59);
        MoneyTransfer.displayTransferHistoryInDateRange(startDate ,endDate);

//        AmountFlowTest.FlowTest();
//        CategorySumTest.sumCategoryTest();
          CategorySumTest.sumCategoryServiceTest();
          AmountFlowTest.AmountTest();

    }
}