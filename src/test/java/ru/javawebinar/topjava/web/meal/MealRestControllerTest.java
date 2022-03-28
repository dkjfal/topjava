package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.Util;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.util.MealsUtil.*;

class MealRestControllerTest extends AbstractControllerTest {
    public static final String GET_ALL_BETWEEN_URL = "/rest/meals/filter?startDate=2020-01-30&startTime=10:00:00&endDate=2020-01-31&endTime=18:00:00";

    @Test
    void getAll() throws Exception {
        perform(get("/rest/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MEAL_TO_MATCHER.contentJson(getTos(meals, DEFAULT_CALORIES_PER_DAY)));
    }

    @Test
    void getBetween() throws Exception {
        List<MealTo> expected = getFilteredTos(
                meals.stream()
                .filter(el -> Util.isBetweenInclusive(el.getDate(), LocalDate.parse("2020-01-30"), LocalDate.parse("2020-01-31")))
                .toList(), DEFAULT_CALORIES_PER_DAY, LocalTime.parse("10:00:00"), LocalTime.parse("18:00:00")
        );

        perform(get(GET_ALL_BETWEEN_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MEAL_TO_MATCHER.contentJson(expected));
    }

    @Test
    void getById() throws Exception {
        perform(get("/rest/meals/100008"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MEAL_MATCHER.contentJson(meal6));
    }

    @Test
    void getNotFound() {
        NestedServletException thrown = assertThrows(NestedServletException.class,
                () -> perform(get("/rest/meals/777"))
        );

        assertTrue(thrown.getCause() instanceof NotFoundException);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete("/rest/meals/100008"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteNotFound() {
        NestedServletException thrown = assertThrows(NestedServletException.class,
                () -> perform(MockMvcRequestBuilders.delete("/rest/meals/777")));

        assertTrue(thrown.getCause() instanceof NotFoundException);
    }

    @Test
    void create() throws Exception {
        Meal newMeal = getNew();
        newMeal.setId(100012);

        perform(post("/rest/meals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNew())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MEAL_MATCHER.contentJson(newMeal));
    }

    @Test
    void update() throws Exception {
        perform(put("/rest/meals/100003")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getUpdated())))
                .andDo(print())
                .andExpect(status().isOk());
    }
}