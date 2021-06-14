package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final Map<Integer, List<Integer>> userMealsRepository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, authUserId()));
    }

    @Override
    public Meal save(Meal meal, int authUserId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            List<Integer> addList = new CopyOnWriteArrayList<>();
            addList = getUserMealList(authUserId);
            if (addList != null) addList.add(meal.getId());
            userMealsRepository.put(authUserId, addList);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int authUserId) {
        List <Integer> uD = getUserMealList(authUserId);
        List <Integer> afterD = uD.stream().filter(u -> u!=id).collect(Collectors.toList());
        userMealsRepository.put(authUserId, afterD);
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int authUserId) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll(int authUserId) {
        List<Integer> userMeals = userMealsRepository.get(authUserId);
        List<Meal> u = new CopyOnWriteArrayList<>();
        for (Integer userMeal : userMeals) {
            u.add(repository.get(userMeal));
        }
        return u.stream().sorted(Comparator.comparing(Meal::getDateTime)).collect(Collectors.toList());
    }

    public List<Integer> getUserMealList(int authUserId) {
        if (userMealsRepository.get(authUserId) == null) return new CopyOnWriteArrayList<>();
        else return userMealsRepository.get(authUserId);
    }

}

