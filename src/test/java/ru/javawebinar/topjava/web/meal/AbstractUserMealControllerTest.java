package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.web.AbstractControllerTest;

/**
 * Created by Mayia on 21.04.2016.
 */
public abstract class AbstractUserMealControllerTest extends AbstractControllerTest {
    @Autowired
    UserMealService userMealService;
}
