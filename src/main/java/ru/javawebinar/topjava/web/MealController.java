package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * Created by Mayia on 16.04.2016.
 */

@Controller
@RequestMapping(value = "/meals")
public class MealController {
    @Autowired
    UserMealRestController mealRestController;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getMeals(HttpServletRequest request){
        return new ModelAndView("mealList", "mealList", mealRestController.getAll());
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public ModelAndView deleteMeal(@RequestParam int id){
        mealRestController.delete(id);
        return new ModelAndView("redirect:/meals");
    }

    @RequestMapping(path = "update", method = RequestMethod.GET)
    public ModelAndView updateMeal (@RequestParam int id){
       final UserMeal meal = mealRestController.get(id);
        return new ModelAndView("mealEdit", "meal", meal);
    }

    @RequestMapping( method = RequestMethod.POST)
    public ModelAndView saveUpdateMeal (HttpServletRequest request){
        final UserMeal meal = new UserMeal (LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        mealRestController.update(meal, Integer.parseInt(request.getParameter("id")));
        return new ModelAndView("redirect:/meals");
    }
}
