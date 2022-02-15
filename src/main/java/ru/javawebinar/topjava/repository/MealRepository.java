package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by Polik on 2/1/2022
 */
public interface MealRepository {
    List<Meal> getAll();
    Meal save(Meal meal);
    boolean delete(int id, int userId);
    Meal get(int id);
}
