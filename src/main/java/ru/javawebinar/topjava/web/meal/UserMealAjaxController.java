package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Mayia on 29.04.2016.
 */
@RestController
@RequestMapping("/ajax/profile/meals")
public class UserMealAjaxController extends AbstractUserMealController{

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getAll() {
        return super.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createOrUpdate(@RequestParam("id") int id,
                               @RequestParam("dateTime")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                               @RequestParam("description") String description,
                               @RequestParam("calories") Integer calories) {

        UserMeal userMeal = new UserMeal(dateTime,description,calories);
        if (id == 0) {
            super.create(userMeal);
        } else {
            super.update(userMeal, id);
        }
    }
}
