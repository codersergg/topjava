package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    /*public MealRestController(MealService service) {
        this.service = service;

    }*/

    public Meal save(Meal meal, int authUserId) {
        isThisYourMeal(authUserId);
        return service.save(meal, authUserId());
    }

    public  boolean delete(int id, int authUserId) {
        isThisYourMeal(authUserId);
        return service.delete(id, authUserId());
    }

    public Meal get(int id, int authUserId) {
        isThisYourMeal(authUserId);
        return service.get(id, authUserId());
    }

    public Collection<Meal> getAll(int authUserId) {
        isThisYourMeal(authUserId);
        return service.getAll(authUserId);
    }

    private void isThisYourMeal(int authUserId) {
        if (authUserId != authUserId()) throw new NotFoundException("it's not your meal");
    }

}