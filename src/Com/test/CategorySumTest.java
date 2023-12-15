package Com.test;

import services.CategorySum;
import utils.CategorySumResult;

import java.time.LocalDateTime;

public class CategorySumTest {
    public static void sumCategoryTest(){
        LocalDateTime startDate = LocalDateTime.of(2023, 12, 14, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 15, 17, 59, 59);
        CategorySumResult result = CategorySum.getCategorySum("Fifaliana" , startDate,endDate);
        System.out.println("Restaurent sum : " + result.getRestaurant());
        System.out.println("Salary sum : " + result.getSalary());

    }
}
