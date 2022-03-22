package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.getFilteredTos;
import static ru.javawebinar.topjava.util.MealsUtil.getTos;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

/**
 * Created by Polik on 3/16/2022
 */
public abstract class AbstractMealController {
    @Autowired
    protected MealService service;

    protected final Logger log = LoggerFactory.getLogger(getClass());

    public Meal get(int id) {
        int userId = authUserId();
        log.info("get meal {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public List<MealTo> getAll() {
        int userId = authUserId();
        log.info("getAll for user {}", userId);
        return getTos(service.getAll(userId), authUserCaloriesPerDay());
    }

    public List<MealTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                   @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        int userId = authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);

        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, userId);
        return getFilteredTos(mealsDateFiltered, authUserCaloriesPerDay(), startTime, endTime);
    }

    public Meal create(Meal meal) {
        int userId = authUserId();
        checkNew(meal);
        log.info("create {} for user {}", meal, userId);
        return service.create(meal, userId);
    }

    public void update(Meal meal, int id) {
        int userId = authUserId();
        assureIdConsistent(meal, id);
        log.info("update {} for user {}", meal, userId);
        service.update(meal, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", id, userId);
        service.delete(id, userId);
    }
}
