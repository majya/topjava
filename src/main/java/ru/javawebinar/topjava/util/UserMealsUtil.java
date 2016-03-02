package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> list = getFilteredMealsWithExceeded(mealList, LocalTime.of(18, 0), LocalTime.of(21,0), 2000);
        list.forEach(System.out::println); // проверка
    }
    public static List<UserMealWithExceed>  getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        ArrayList<UserMealWithExceed> list = new ArrayList<>(); // это список для выходных данных
        HashMap<LocalDate, Integer> hashMap = new HashMap<>(); // этот мап для подсчета калорий по дням

        //Прохожусь по всем значениям переданного списка. Подсчитываю калорий за день и кидаю в hashMap, где дата - это ключ, а сумма калорий - значение
        // Знаю, что делаю лишние операции, но что-то я не могу придумать, как их избежать
        for (UserMeal meal: mealList){
            LocalDate localDate=toLocalDate(meal.getDateTime());
            if(hashMap.containsKey(localDate)){
                Integer sum = hashMap.get(localDate);
                sum = sum + meal.getCalories();
                hashMap.put(localDate, sum);
            }
            else hashMap.put(localDate,meal.getCalories());
        }

        // Я решила пройтись в цикле по списку mealList, проверить, попадают ли элементы списка в заданное время.
        // Если попадают, то создаем объект userMealWithExceed, поле exceed вычисляем по сумме калорий, которая хранится в hashMap
        // Как оптимизировать??? Может метод создать, который будет содержать цикл, который я создала выше. И вызывать этот метод только по мере необходимости

        for (UserMeal userMeal: mealList){
            LocalTime localTime = toLocalTime(userMeal.getDateTime());
            if (TimeUtil.isBetween(localTime, startTime, endTime)){

                boolean exceed = false;
                LocalDate localDate = toLocalDate(userMeal.getDateTime());
                if (hashMap.get(localDate) > caloriesPerDay) exceed = true;
                UserMealWithExceed userMealWithExceed = new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(),userMeal.getCalories(), exceed);
                list.add(userMealWithExceed);
            }
        }
        return list;
    }
    public static LocalDate toLocalDate (LocalDateTime dateTime){
        return dateTime.toLocalDate();
    }
    public static LocalTime toLocalTime (LocalDateTime dateTime){
        return dateTime.toLocalTime();
    }
}
