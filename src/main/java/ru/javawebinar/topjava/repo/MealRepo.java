package ru.javawebinar.topjava.repo;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealRepo {

    List<MealTo> getAll();

    void add(Meal meal);

    void delete(int id);

    void edit(int id);

}
