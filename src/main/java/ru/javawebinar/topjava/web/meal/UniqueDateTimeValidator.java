package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Polik on 4/19/2022
 */
@Component
public class UniqueDateTimeValidator implements Validator {
    private final MealRepository repository;
    private final HttpServletRequest request;

    public UniqueDateTimeValidator(MealRepository repository, HttpServletRequest request) {
        this.repository = repository;
        this.request = request;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(Meal.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Meal meal = (Meal) target;
        Meal fromDb = MealsUtil.getByDateTime(repository.getAll(SecurityUtil.authUserId()), meal.getDateTime());

        if (fromDb != null) {
            if (request.getMethod().equals("PUT")) {
                int id = fromDb.id();

                if (meal.getId() != null && meal.getId() == id) {
                    return;
                }

                String requestURI = request.getRequestURI();

                if (requestURI.endsWith("/" + id)) {
                    return;
                }
            }

            throw new IllegalRequestDataException("User with such date time already exists");
        }
    }
}
