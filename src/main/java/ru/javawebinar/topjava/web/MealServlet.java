package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import ru.javawebinar.topjava.model.UserMealsStore;
import ru.javawebinar.topjava.util.UserMealsUtil;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Mayia on 10.03.2016.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to mealList");
        List<UserMeal> mealList = UserMealsStore.getUserMealStore();
        List<UserMealWithExceed> filteredMealsWithExceeded =
                UserMealsUtil.getFilteredMealsWithExceeded(mealList, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);

        request.setAttribute("meals", filteredMealsWithExceeded);
        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
    }
}
