package ru.javawebinar.topjava.web.meal;

import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.util.List;

@RestController
@RequestMapping("/rest/meals")
public class MealRestController extends AbstractMealController {

    @Override
    @GetMapping({"/", ""})
    public List<MealTo> getAll () {
        return super.getAll();
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

    @Override
    @PostMapping()
    public Meal create(@RequestBody Meal meal) {
        return super.create(meal);
    }
}