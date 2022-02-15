package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenHalfOpen;

public class MealsUtil {

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static <T extends Comparable<T>> List<MealTo> filteredByStreams(List<Meal> meals, T startTime, T endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(Collectors.groupingBy(Meal::getLocalDate
                        , Collectors.summingInt(Meal::getCalories)));

        List<MealTo> result = new ArrayList<>();

        meals.forEach(el -> {
            if (isBetweenHalfOpen(el.getDateTime(), startTime, endTime))
                result.add(new MealTo(el.getId(), el.getDateTime(), el.getDescription(), el.getCalories()
                        , caloriesSumByDate.get(el.getLocalDate()) > caloriesPerDay));
        });

        return result;
    }

    public static List<Meal> filteredByUserId(List<Meal> meals, int userId) {
        return meals.stream()
                .filter(el -> el.getUserId() == userId)
                .collect(Collectors.toList());
    }
}
