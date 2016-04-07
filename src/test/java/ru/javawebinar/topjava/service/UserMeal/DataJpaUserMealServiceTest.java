package ru.javawebinar.topjava.service.UserMeal;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserMealServiceTest;

/**
 * Created by Mayia on 08.04.2016.
 */

@ActiveProfiles({Profiles.POSTGRES, Profiles.DATAJPA})
public class DataJpaUserMealServiceTest extends UserMealServiceTest {
}
