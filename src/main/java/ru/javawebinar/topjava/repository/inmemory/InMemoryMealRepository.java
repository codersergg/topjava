package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        else {
            if (isMealExistById(meal.getId(), userId)) {
                return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
            } else return null;
        }
    }

    @Override
    public boolean delete(int id, Integer userId) {
        if (userId.equals(repository.get(id).getUserId())) {
            return repository.remove(id) != null;
        } else return false;
    }

    @Override
    public Meal get(int id, Integer userId) {
        if (userId.equals(repository.get(id).getUserId())) {
            return repository.get(id);
        } else return null;
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDateTime))
                .collect(Collectors.toList());
    }

    private boolean isMealExistById(int id, Integer userId) {
        Meal meal = repository.get(id);
        return userId.equals(meal.getUserId());
    }
}

