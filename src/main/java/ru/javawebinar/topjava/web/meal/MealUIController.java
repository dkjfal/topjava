package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.*;

@RestController
@RequestMapping(value = "/profile/meals", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealUIController extends AbstractMealController {

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestParam @DateTimeFormat(iso = DATE_TIME) LocalDateTime dateTime,
                       @RequestParam String description,
                       @RequestParam int calories) {
        Meal meal = new Meal(dateTime, description, calories);
        super.create(meal);
    }

    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @GetMapping(value = "/filter", produces = "application/json")
    public List<MealTo> getBetween(@RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DATE) LocalDate startDate,
                                   @RequestParam(name = "startTime", required = false) @DateTimeFormat(iso = TIME) LocalTime startTime,
                                   @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DATE) LocalDate endDate,
                                   @RequestParam(name = "endTime", required = false) @DateTimeFormat(iso = TIME) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}
