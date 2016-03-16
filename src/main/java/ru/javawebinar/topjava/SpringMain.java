package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.meal.UserMealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));
//            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
//            System.out.println(adminUserController.create(new User(1, "userName", "email", "password", Role.ROLE_ADMIN)));
            UserMealRestController userMealRestController = appCtx.getBean(UserMealRestController.class);
            System.out.println(userMealRestController.getAll());
            System.out.println(userMealRestController.get(18));
            try {
                System.out.println(userMealRestController.get(0));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            userMealRestController.create(new UserMeal(LocalDateTime.now(), "Eggs", 500));

            userMealRestController.delete(13);

            try {
                userMealRestController.delete(2);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            userMealRestController.update(new UserMeal(LocalDateTime.now(), "Soup", 300), 14);
            System.out.println(userMealRestController.getAll());

            System.out.println(userMealRestController.getFilteredList(LocalDate.of(2015, 4, 1), LocalTime.NOON, LocalDate.of(2015, 5, 30), LocalTime.MAX));

        }
    }
}
