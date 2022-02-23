package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringRunner.class)
@Sql(scripts = {"classpath:db/initDB.sql", "classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    @Autowired
    public MealService service;

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(ADMIN_MEAL_3.getId(), USER_ID));
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () -> service.create(duplicateDateTime(), USER_ID));
    }

    @Test
    public void get() {
        assertNotNull(service.get(USER_MEAL_2.getId(), USER_ID));
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_4.getId(), USER_ID);
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = service.getBetweenInclusive(LocalDate.parse("2021-12-28"), LocalDate.parse("2022-02-02"), USER_ID);
        assertEquals(meals.size(), 6);
    }

    @Test
    public void getAll() {
        assertEquals(service.getAll(USER_ID).size(), 7);
    }

    @Test
    public void update() {
        Meal meal = getUpdated();
        service.update(meal, USER_ID);
    }

    @Test
    public void create() {
        assertNotNull(service.create(getNew(), ADMIN_ID));
    }
}