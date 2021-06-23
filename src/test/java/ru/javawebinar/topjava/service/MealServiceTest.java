package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app-jdbc.xml",
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL_1, USER_ID);
        MealTestData.assertMatch(meal, userMeal1);
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_1, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_1, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> betweenInclusive = service.getBetweenInclusive(START_TIME, END_TIME, USER_ID);
        MealTestData.assertMatch(betweenInclusive, userMeal2, userMeal1);
    }

    @Test
    public void getBetweenNull() {
        List<Meal> betweenInclusive = service.getBetweenInclusive(null, null, USER_ID);
        MealTestData.assertMatch(betweenInclusive, userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        MealTestData.assertMatch(all, userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_MEAL_1, USER_ID), MealTestData.getUpdated());
    }

    @Test
    public void create() {
        Meal created = service.create(MealTestData.getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    //delete, get, update

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_MEAL_1, NOT_FOUND));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_1, NOT_FOUND));
    }

    @Test
    public void updateNotFound() {
        Meal updatedTest = MealTestData.getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updatedTest, NOT_FOUND));
    }

    @Test
    public void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(LocalDateTime.of(2021, Month.MAY, 1, 10, 0), "завтрак", 500), USER_ID));
    }
}