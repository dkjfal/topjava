package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class MealsUtil {

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(Collectors.groupingBy(Meal::getLocalDate
                        , Collectors.summingInt(Meal::getCalories)));

        List<MealTo> result = new ArrayList<>();

        meals.forEach(el -> {
            if (isBetweenHalfOpen(el.getLocalTime(), startTime, endTime))
                result.add(new MealTo(el.getId(), el.getDateTime(), el.getDescription(), el.getCalories()
                        , caloriesSumByDate.get(el.getLocalDate()) > caloriesPerDay));
        });

        return result;
    }
}
