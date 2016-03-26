package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static ru.javawebinar.topjava.MealTestData.*;


/**
 * Created by Mayia on 26.03.2016.
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceImplTest {

    @Autowired
    protected UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;

    public static final Comparator<UserMeal> COMPARATOR = (o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime());
    public static final int USER_ID = UserTestData.USER_ID;
    public static final int ADMIN_ID = UserTestData.ADMIN_ID;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }


    @Test
    public void testSave() throws Exception {
        UserMeal userMeal = new UserMeal(LocalDateTime.now(), "Test_Meal", 600);
        UserMeal created = service.save(userMeal, USER_ID);
        userMeal.setId(created.getId());
        List<UserMeal> testUserMealList = new ArrayList<>();
        testUserMealList.addAll(USER_LIST);
        testUserMealList.add(userMeal);
        Collections.sort(testUserMealList, COMPARATOR);
        MATCHER.assertCollectionEquals(testUserMealList, service.getAll(UserTestData.USER_ID));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(ADMIN_LIST.get(1).getId(), ADMIN_ID);
        List<UserMeal> userMeals = new ArrayList<>();
        userMeals.addAll(ADMIN_LIST);
        userMeals.remove(1);
        Collections.sort(userMeals, COMPARATOR);
        MATCHER.assertCollectionEquals(userMeals, service.getAll(ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteForeignMeal() throws Exception {
        service.delete(USER_LIST.get(0).getId(), ADMIN_LIST.get(0).getId());
    }

    @Test
    public void testGet() throws Exception {
        UserMeal userMeal = service.get(USER_LIST.get(0).getId(), USER_ID);
        MATCHER.assertEquals(USER_LIST.get(0), userMeal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetForeignMeal() throws Exception {
        UserMeal userMeal = service.get(USER_LIST.get(0).getId(), ADMIN_ID);
        MATCHER.assertEquals(USER_LIST.get(0), userMeal);
    }


    @Test
    public void testGetBetweenDateTimes() throws Exception {
        service.getBetweenDateTimes(LocalDateTime.of(2015, 5, 30, 0, 0), LocalDateTime.of(2015, 5, 30, 0, 0), ADMIN_ID);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<UserMeal> all = service.getAll(USER_ID);
        Collections.sort(USER_LIST, COMPARATOR);
        MATCHER.assertCollectionEquals(USER_LIST, all);
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal updated = USER_LIST.get(0);
        updated.setDescription("UpdatedName");
        updated.setCalories(100);
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, service.get(USER_LIST.get(0).getId(),USER_ID));
    }
}