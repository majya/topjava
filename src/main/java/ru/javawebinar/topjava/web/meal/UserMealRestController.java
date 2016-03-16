package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.to.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */

@Controller
public class UserMealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMealService service;

    public List<UserMealWithExceed> getAll() {
        LOG.info("getAll");
        return UserMealsUtil.getWithExceeded(service.getAll(LoggedUser.id()), LoggedUser.getCaloriesPerDay());
    }

    public List<UserMealWithExceed> getFilteredList(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        LOG.info("getFilteredList");
        List<UserMeal> withExceedList = service.getAll(LoggedUser.id());
        List<UserMeal> sortedByDateList = new ArrayList<>();

        for (UserMeal meal : withExceedList) {
            LocalDate localDate = meal.getDateTime().toLocalDate();
            if (TimeUtil.isBetweenDate(localDate, startDate, endDate)) {
                sortedByDateList.add(meal);
            }
        }
        return UserMealsUtil.getFilteredWithExceededByCycle(sortedByDateList, startTime, endTime, LoggedUser.getCaloriesPerDay());
    }

    public UserMeal get(int id) {
        LOG.info("get");
        UserMeal userMeal = service.get(LoggedUser.id(), id);
        if (userMeal == (null)) throw new NotFoundException("Meal is not exist");
        return userMeal;
    }

    public void delete(int id) {
        LOG.info("delete");
        boolean isMealExist = service.delete(LoggedUser.id(), id);
        if (!isMealExist) throw new NotFoundException("Meal is not exist");
    }

    public void create(UserMeal userMeal) {
        LOG.info("create " + userMeal);
        service.save(LoggedUser.id(), userMeal);
    }

    public void update(UserMeal userMeal, int id) {
        userMeal.setId(id);
        LOG.info("update " + userMeal);
        service.save(LoggedUser.id(), userMeal);
    }

}
