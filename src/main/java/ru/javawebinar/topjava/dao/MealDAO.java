package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by Polik on 2/1/2022
 */
public interface MealDAO {
    List<Meal> getAll();
    void save(Meal meal);
    void delete(int id);
}
