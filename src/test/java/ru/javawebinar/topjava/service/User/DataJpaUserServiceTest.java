package ru.javawebinar.topjava.service.User;

import org.hibernate.LazyInitializationException;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;


import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by Mayia on 07.04.2016.
 */
@ActiveProfiles({Profiles.POSTGRES, Profiles.DATAJPA})
public class DataJpaUserServiceTest extends UserServiceTest {
    @Test
    public void testGetWithUserMeal() throws Exception {
        User withMeals = service.getWithMeals(UserTestData.USER_ID);
        USER.setUserMeals(USER_MEALS);
        UserTestData.MATCHER.assertEquals(USER, withMeals);
        MealTestData.MATCHER.assertCollectionEquals(USER.getUserMeals(),withMeals.getUserMeals());
    }

    @Test(expected = LazyInitializationException.class)
    public void testGetWithoutUserMeal() throws Exception {
        User expectedUser = service.get(USER_ID);
        System.out.println(expectedUser.getUserMeals().toString());
    }
}
