package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface DBMealUtil {

    static DBMealUtil getConnect() {
        return new MealsUtil();
    }

    List<MealTo> getAllMeals();

    void addMeal(Meal meal);

    void deleteMeal(Meal meal);

    void editMeal(Meal meal);

}
