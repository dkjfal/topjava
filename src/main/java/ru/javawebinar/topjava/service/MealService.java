package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;

import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByUserId;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    public Meal get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<MealTo> getAll(int userId) {

        return filteredByStreams(filteredByUserId(repository.getAll(), userId)
                , null, null, 2000);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public void update(Meal meal) {
        repository.save(meal);
    }

    public <T extends Comparable<T>> List<MealTo> getAllBetweenSelectedDate(T from, T to, int userId) {
        return filteredByStreams(filteredByUserId(repository.getAll(), userId)
                , from, to, 2000);
    }
}