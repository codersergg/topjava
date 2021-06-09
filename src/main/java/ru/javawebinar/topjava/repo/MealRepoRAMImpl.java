package ru.javawebinar.topjava.repo;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

public class MealRepoRAMImpl implements MealRepo {

    @Override
    public List<MealTo> getAll() {
        return MealsUtil.filteredByStreams(MealsUtil.getMeals(), LocalTime.MIN, LocalTime.MAX, MealsUtil.caloriesPerDay);
    }

    @Override
    public void add(Meal meal) {
        MealsUtil.meals.add(meal);
    }

    @Override
    public void delete(int id) {
        MealsUtil.meals.removeIf(meal -> meal.getId() == id);
    }

    @Override
    public void edit(int id) {
        for (Meal meal : MealsUtil.meals) {
            if (meal.getId() == id) {
                int idIndex = MealsUtil.meals.indexOf(meal);
                MealsUtil.meals.set(idIndex, new Meal(id, meal.getDateTime(), meal.getDescription(), meal.getCalories()));
            }
        }
    }
}
