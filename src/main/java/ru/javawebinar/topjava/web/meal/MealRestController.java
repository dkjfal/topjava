package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping(value = "/rest/meals", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {

    @GetMapping({"", "/"})
    public List<MealTo> getBetween() {
        return super.getAll();
    }

    @Override
    @GetMapping({"/filter", "/filter/"})
    public List<MealTo> getBetween(@RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DATE) LocalDate startDate,
                                   @RequestParam(name = "startTime", required = false) @DateTimeFormat(iso = TIME) LocalTime startTime,
                                   @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DATE) LocalDate endDate,
                                   @RequestParam(name = "endTime", required = false) @DateTimeFormat(iso = TIME) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }

    @Override
    @GetMapping({"/{id}", "/{id}/"})
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping({"/{id}", "/{id}/"})
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping({"/", ""})
    public Meal create(@RequestBody Meal meal) {
        return super.create(meal);
    }

    @PutMapping({"/{id}", "/{id}/"})
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        super.update(meal, id);
    }
}