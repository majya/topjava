package ru.javawebinar.topjava.service.UserMeal;

import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealServiceTest;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by Mayia on 08.04.2016.
 */

@ActiveProfiles({Profiles.POSTGRES, Profiles.DATAJPA})
public class DataJpaUserMealServiceTest extends UserMealServiceTest {
    @Test
    public void testGetWithUser() throws Exception {
        UserMeal meal = service.getMealWithUser(MEAL1, USER_ID);
        MEAL1.setUser(USER);
        MATCHER2.assertEquals(MEAL1,meal);
    }

    @Test
    public void testGetWithoutUser()throws Exception{
        thrown.expect(LazyInitializationException.class);
        UserMeal meal = service.get(MealTestData.MEAL1_ID, USER_ID);
        MEAL1.setUser(USER);
        MATCHER2.assertEquals(MEAL1,meal);
    }
}
