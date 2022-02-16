package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Polik on 2/3/2022
 */
@Repository
public class InMemoryMealRepository implements MealRepository {
    private final ConcurrentHashMap<Integer, Meal> meals;
    private final AtomicInteger lastId;

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(lastId.getAndIncrement());
        }

        return meals.put(meal.getId(), meal);
    }

    @Override
    public Meal get(int id) {
        return meals.get(id);
    }

    @Override
    public boolean delete(int id, int userId) {
        return meals.entrySet()
                .removeIf(el -> el.getKey() == id && el.getValue().getUserId() == userId);
    }

    {
        lastId = new AtomicInteger(1);
        meals = new ConcurrentHashMap<>();
    }
}
