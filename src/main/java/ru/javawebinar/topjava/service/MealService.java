package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal save(Meal meal, int authUserId) {
        return repository.save(meal, authUserId);
    }

    public boolean delete(int id, int authUserId) {
        return repository.delete(id, authUserId);
    }

    public Meal get(int id, int authUserId) {
        return repository.get(id, authUserId);
    }

    public Collection<Meal> getAll(int authUserId) {
        return repository.getAll(authUserId);
    }

}