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

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal));
    }

    @Override
    public Meal save(Meal meal) {
        if (isMealExistByMeal(meal)) {
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                repository.put(meal.getId(), meal);
                return meal;
            }
            // handle case: update, but not present in storage
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        } else return null;
    }

    @Override
    public boolean delete(int id) {
        if (isMealExistById(id)) {
            return repository.remove(id) != null;
        } else return false;
    }

    @Override
    public Meal get(int id) {
        if (isMealExistByMeal(repository.get(id))) {
            return repository.get(id);
        } else return null;
    }

    @Override
    public List<Meal> getAll() {
        return repository.values().stream()
                .filter(this::isMealExistByMeal)
                .sorted(Comparator.comparing(Meal::getDateTime))
                .collect(Collectors.toList());
    }

    private boolean isMealExistById(int id) {
        return isMealExistByMeal(repository.get(id));
    }

    private boolean isMealExistByMeal(Meal meal) {
        return meal.getUserId().equals(authUserId());
    }
}

