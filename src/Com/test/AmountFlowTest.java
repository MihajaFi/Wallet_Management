package Com.test;

import services.AmountFlowMap;
import utils.AmountFlowResult;

import java.time.LocalDateTime;

public class AmountFlowTest {
    public static void FlowTest(){
        LocalDateTime startDate = LocalDateTime.of(2023, 12, 14, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 15, 17, 59, 59);
       AmountFlowResult result = AmountFlowMap.getAmountFlow("Fifaliana" , startDate , endDate) ;
        System.out.println("Total credit : " + result.getTotalCredit());
        System.out.println("Total debit : " + result.getTotalDebit());

    }
}
