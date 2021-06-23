package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL_1 = START_SEQ + 2;
    public static final int USER_MEAL_2 = START_SEQ + 3;
    public static final int USER_MEAL_3 = START_SEQ + 4;
    public static final int ADMIN_MEAL_1 = START_SEQ + 5;
    public static final int ADMIN_MEAL_2 = START_SEQ + 6;
    public static final int ADMIN_MEAL_3 = START_SEQ + 7;

    public static final int NOT_FOUND = 100_010;

    public static final LocalDate START_TIME = LocalDate.of(2021, Month.MAY, 1);
    public static final LocalDate END_TIME = LocalDate.of(2021, Month.MAY, 2);

    public static final Meal userMeal1 = new Meal(USER_MEAL_1, LocalDateTime.of(2021, Month.MAY, 1, 10, 0), "завтрак", 500);
    public static final Meal userMeal2 = new Meal(USER_MEAL_2, LocalDateTime.of(2021, Month.MAY, 1, 12, 0), "обед", 600);
    public static final Meal userMeal3 = new Meal(USER_MEAL_3, LocalDateTime.of(2021, Month.MAY, 3, 17, 0), "ужин", 700);
    public static final Meal adminMeal1 = new Meal(ADMIN_MEAL_1, LocalDateTime.of(2021, Month.MAY, 1, 10, 0), "завтрак Админа", 1300);
    public static final Meal adminMeal2 = new Meal(ADMIN_MEAL_2, LocalDateTime.of(2021, Month.MAY, 1, 12, 0), "обед Админа", 400);
    public static final Meal adminMeal3 = new Meal(ADMIN_MEAL_3, LocalDateTime.of(2021, Month.MAY, 1, 12, 0), "ужин Админа", 500);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2021, Month.MAY, 1, 0, 0), "еда", 333);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMeal1);
        updated.setDescription("новая еда");
        updated.setDateTime(LocalDateTime.of(2021, Month.MAY, 2, 8, 0));
        updated.setCalories(3333);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
