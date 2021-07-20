package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public abstract class AbstractMealController {

    @Autowired
    private MealRestController mealRestController;

    public void delete(int id) {
        mealRestController.delete(id);
    }

    public Meal update(int id) {
        Meal meal = mealRestController.get(id);
        return meal;
    }

    public List<MealTo> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        List<MealTo> meals = mealRestController.getBetween(startDate, startTime, endDate, endTime);
        return meals;
    }
}
