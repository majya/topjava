package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.web.meal.UserMealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml","spring/spring-db.xml")) {
            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
//            adminUserController.delete(100000);
//            System.out.println(adminUserController.create(UserTestData.USER));
//            System.out.println();

            UserMealRestController mealController = appCtx.getBean(UserMealRestController.class);
//            List<UserMealWithExceed> filteredMealsWithExceeded =
//                    mealController.getBetween(
//                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
//                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
//            filteredMealsWithExceeded.forEach(System.out::println);

            UserMeal userMeal = new UserMeal(LocalDateTime.now(), "Lunch", 510);
//            mealController.create(userMeal);
//            mealController.delete(100005);
//            mealController.update(userMeal,100009);
            UserMeal meal = mealController.get(100002);
            System.out.println(meal.getDateTime().toLocalTime());
            List<UserMealWithExceed> all = mealController.getAll();
//            all.forEach(userMealWithExceed -> System.out.println(userMealWithExceed));
            List<UserMealWithExceed> between = mealController.getBetween(null, LocalTime.of(10, 1), null, LocalTime.of(14, 1));
            between.forEach(System.out::println);


        }
    }
}
