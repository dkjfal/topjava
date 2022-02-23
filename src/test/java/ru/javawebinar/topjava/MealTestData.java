package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static ru.javawebinar.topjava.UserTestData.*;

/**
 * Created by Polik on 2/21/2022
 */
public class MealTestData {

    public static final Meal USER_MEAL_1 = new Meal(100003, LocalDateTime.parse("2022-02-02T15:43:00.000"), "Завтрак", 1337);
    public static final Meal USER_MEAL_2 = new Meal(100004, LocalDateTime.parse("2022-02-02T15:41:00.000"), "Ужин", 1337);
    public static final Meal USER_MEAL_3 = new Meal(100006, LocalDateTime.parse("2022-01-16T20:00:00.000"), "Описание", 1337);
    public static final Meal USER_MEAL_4 = new Meal(100008, LocalDateTime.parse("2022-01-13T13:00:00.000"), "Еда на граничное значение", 1337);
    public static final Meal USER_MEAL_5 = new Meal(100009, LocalDateTime.parse("2022-01-09T05:00:00.000"), "Завтрак", 1337);
    public static final Meal USER_MEAL_6 = new Meal(100011, LocalDateTime.parse("2021-12-28T10:57:00.000"), "Обед", 1337);
    public static final Meal USER_MEAL_7 = new Meal(100012, LocalDateTime.parse("2021-12-09T13:34:00.000"), "Ужин", 1337);

    public static final Meal ADMIN_MEAL_1 = new Meal(100005, LocalDateTime.parse("2020-12-21T09:43:00.000"), "Обед", 1337);
    public static final Meal ADMIN_MEAL_2 = new Meal(100007, LocalDateTime.parse("2021-03-14T15:41:00.000"), "Ужин", 1337);
    public static final Meal ADMIN_MEAL_3 = new Meal(100010, LocalDateTime.parse("2020-09-25T18:12:00.000"), "Завтрак", 1337);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.parse("2020-06-09T11:31:00.000"), "new meal", 1337);
    }

    public static Meal duplicateDateTime() {
        return new Meal(null, LocalDateTime.parse("2022-01-09T05:00:00.000"), "Duplicate LocalDateTime", 1337);
    }

    public static Meal getUpdated() {
        return new Meal(100009, LocalDateTime.parse("2022-01-09T05:00:00.000"), "Updated meal", 1337);
    }

}
