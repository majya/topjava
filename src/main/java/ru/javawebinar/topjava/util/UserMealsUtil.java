package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;


/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> list = getFilteredMealsWithExceeded(mealList, LocalTime.of(18, 0), LocalTime.of(21, 0), 2000);
        list.forEach(System.out::println);
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        Map<LocalDate, Integer> localDateIntegerHashMap = new HashMap<>();


        for (UserMeal meal : mealList) {
            LocalDate localDate = meal.getDateTime().toLocalDate();
            if (localDateIntegerHashMap.containsKey(localDate)) {
                localDateIntegerHashMap.put(localDate, localDateIntegerHashMap.get(localDate) + meal.getCalories());
            } else localDateIntegerHashMap.put(localDate, meal.getCalories());
        }

        for (UserMeal userMeal : mealList) {
            LocalTime localTime = userMeal.getDateTime().toLocalTime();
            UserMealWithExceed userMealWithExceed;
            if (TimeUtil.isBetween(localTime, startTime, endTime)) {
                LocalDate localDate = userMeal.getDateTime().toLocalDate();
                if (localDateIntegerHashMap.get(localDate) > caloriesPerDay)
                    userMealWithExceed = new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true);
                else
                    userMealWithExceed = new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), false);
                userMealWithExceedList.add(userMealWithExceed);
            }
        }
        return userMealWithExceedList;
    }
}
