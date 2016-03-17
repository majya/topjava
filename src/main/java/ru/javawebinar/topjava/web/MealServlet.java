package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.to.UserMealWithExceed;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */


public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private UserMealRestController controller;
    ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(UserMealRestController.class);
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {


        String action = "";
        try {
            action = request.getParameter("action");
        } catch (Exception e) {
            LOG.info(e.getMessage());
        }


        if (action != null && action.equals("filter")) {
            String startDateString = request.getParameter("startDate");
            String endDateString = request.getParameter("endDate");
            String startTimeString = request.getParameter("startTime");
            String endTimeString = request.getParameter("endTime");

            LocalDate startDate = startDateString.isEmpty() ? LocalDate.MIN : LocalDate.parse(startDateString);
            LocalDate endDate = endDateString.isEmpty() ? LocalDate.MAX : LocalDate.parse(endDateString);
            LocalTime startTime = startTimeString.isEmpty() ? LocalTime.MIN : LocalTime.parse(startTimeString);
            LocalTime endTime = endTimeString.isEmpty() ? LocalTime.MAX : LocalTime.parse(endTimeString);

            List<UserMealWithExceed> filteredList = controller.getFilteredList(startDate, startTime, endDate, endTime);

            request.setAttribute("mealList", filteredList);
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        }

        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        LOG.info(userMeal.isNew() ? "Create {}" : "Update {}", userMeal);
        if (userMeal.getId() == null) controller.create(userMeal);
        else controller.update(userMeal, userMeal.getId());
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("mealList",
                    controller.getAll());
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            controller.delete(id);
            response.sendRedirect("meals");
        } else {
            final UserMeal meal = action.equals("create") ?
                    new UserMeal(LocalDateTime.now(), "", 1000) :
                    controller.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
