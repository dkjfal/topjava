package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Polik on 2/3/2022
 */
public class MealDAOImpl implements MealDAO {
    private final ConcurrentHashMap<Integer, Meal> meals;
    private final AtomicInteger lastId;

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public void save(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(lastId.getAndIncrement());
            meals.put(meal.getId(), meal);
        } else {
            int id = meal.getId();
            meals.put(id, meal);
        }
    }

    @Override
    public Meal get(int id) {
        return meals.get(id);
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }

    {
        lastId = new AtomicInteger(1);
        meals = new ConcurrentHashMap<>();
        save(new Meal(lastId.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(lastId.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        save(new Meal(lastId.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        save(new Meal(lastId.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        save(new Meal(lastId.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(lastId.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        save(new Meal(lastId.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }
}
