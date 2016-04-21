package ru.javawebinar.topjava.web.user;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.web.AbstractControllerTest;

/**
 * Created by Mayia on 21.04.2016.
 */
public abstract class AbstractUserControllerTest extends AbstractControllerTest {
    @Autowired
    protected UserService userService;

    @Before
    public void setUp() {
        userService.evictCache();
    }
}
