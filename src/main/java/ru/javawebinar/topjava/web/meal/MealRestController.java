package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkUserIdAttachment;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final MealService service;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public MealRestController(MealService mealService) {
        this.service = mealService;
    }

    public Meal create(Meal meal) {
        checkUserIdAttachment(meal, authUserId());
        log.info("create {}", meal);

        return service.create(meal);
    }

    public Meal get(int id) {
        log.info("get {}", id);

        return checkUserIdAttachment(service.get(id), authUserId());
    }

    public <T extends Comparable<T>> List<MealTo> getAllBetweenSelectedDate(T from, T to) {
        log.info("getAllBetweenSelectedDate");
        return service.getAllBetweenSelectedDate(from, to, authUserId());
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return service.getAll(authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public void update(Meal meal) {
        log.info("update {} with id={}", meal, meal.getUserId());
        checkUserIdAttachment(meal, authUserId());

        service.update(meal);
    }
}