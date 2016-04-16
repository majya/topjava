package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by Mayia on 16.04.2016.
 */

@Controller
@RequestMapping(value = "/meals")
public class JspUserMealController extends AbstractUserMealController {


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getMeals() {
        return new ModelAndView("mealList", "mealList", super.getAll());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteMeal(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        super.delete(id);
        return "redirect:/meals";
    }

    @RequestMapping(path = "/update", method = RequestMethod.GET)
    public ModelAndView updateMeal(@RequestParam int id) {
        final UserMeal meal = super.get(id);
        return new ModelAndView("mealEdit", "meal", meal);
    }

    @RequestMapping (method = RequestMethod.POST)
    public String saveCreatedMeal(HttpServletRequest request){
        final UserMeal meal = new UserMeal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        if(request.getParameter("id").isEmpty()){
            super.create(meal);
        }
        else {
            super.update(meal,Integer.parseInt(request.getParameter("id")));
        }
        return "redirect:/meals";
    }

    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public String createMeal(HttpServletRequest request, Model model){
        final UserMeal meal = new UserMeal(LocalDateTime.now(), "", 1000);
        model.addAttribute("meal",meal);
        return "mealEdit";
    }
    @RequestMapping (value = "/filter")
    public String filter(HttpServletRequest request, Model model){
        LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
        LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
        LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
        LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));
        model.addAttribute("mealList", super.getBetween(startDate, startTime, endDate, endTime));
        return "mealList";
    }
    private String resetParam(String param, HttpServletRequest request) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }
}

