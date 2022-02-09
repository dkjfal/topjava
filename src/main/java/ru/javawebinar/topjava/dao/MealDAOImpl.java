package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Polik on 2/3/2022
 */
public class MealDAOImpl implements MealDAO {
    private final List<Meal> meals;
    private final AtomicInteger lastId;

    @Override
    public List<Meal> getAll() {
        return meals;
    }

    @Override
    public synchronized void save(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(lastId.incrementAndGet());
            meals.add(meal);
        } else {
            int id = meal.getId();
            for (int i = 0; i < meals.size(); i++) {
                if (meals.get(i).getId() == id) {
                    meals.set(i, meal);
                    return;
                }
            }
        }
    }

    @Override
    public synchronized void delete(int id) {
        for (int i = 0; i < meals.size(); i++) {
            if (meals.get(i).getId() == id) {
                meals.remove(i);
                return;
            }
        }
    }

    {
        meals = new LinkedList<Meal>() {
        {
            add(new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
            add(new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
            add(new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
            add(new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
            add(new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
            add(new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
            add(new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        }};
        lastId = new AtomicInteger(meals.size());
    }
}
