package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>((java.util.function.Function<UserMeal, String>) new Function<UserMeal, String>() {
        @Override
        public String apply(UserMeal userMeal) {
            return userMeal.toString();
        }
    });

    public static final AtomicInteger id = new AtomicInteger(UserTestData.ADMIN_ID);

    public static final List<UserMeal> USER_LIST = Arrays.asList(
            new UserMeal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new UserMeal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new UserMeal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new UserMeal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new UserMeal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new UserMeal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public static final List<UserMeal> ADMIN_LIST = Arrays.asList(
            new UserMeal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Админ_Завтрак", 500),
            new UserMeal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Админ_Обед", 1000),
            new UserMeal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Админ_Ужин", 500),
            new UserMeal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Админ_Завтрак", 1000),
            new UserMeal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Админ_Обед", 500),
            new UserMeal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Админ_Ужин", 510)
    );
}
