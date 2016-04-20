package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.USER_MEALS;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * Created by Mayia on 20.04.2016.
 */
public class UserMealControllerTest extends AbstractControllerTest {
    @Test
    public void testMealList() throws Exception {
        mockMvc.perform(get("/meals")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("mealList"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/mealList.jsp"))
                .andExpect(model().attribute("mealList", hasSize(USER_MEALS.size())))
                .andExpect(model().attribute("mealList", hasItem(allOf(
                        hasProperty("id", is(USER_MEALS.get(0).getId())),
                        hasProperty("description",is(USER_MEALS.get(0).getDescription()))
                ))))
        ;
    }
}
