package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Query("SELECT meal FROM Meal meal WHERE meal.user.id=:userId ORDER BY meal.dateTime DESC")
    List<Meal> getAll(@Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal meal WHERE meal.id=:id AND meal.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Transactional
    @Override
    <S extends Meal> S save(S entity);

    @Transactional
    @Query("SELECT meal FROM Meal meal WHERE meal.user.id=:userId AND meal.dateTime >=:startDateTime AND meal.dateTime <:endDateTime ORDER BY meal.dateTime DESC")
    List<Meal> getBetweenHalfOpen(@Param("startDateTime") LocalDateTime startDateTime,
                                  @Param("endDateTime") LocalDateTime endDateTime,
                                  @Param("userId") int userId);
}
