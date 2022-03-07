package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    Meal findByIdAndUserId(int id, int userId);

    List<Meal> findByUserIdOrderByDateTimeDesc(int userId);

    @Query(value = "SELECT * FROM meals m WHERE m.user_id = ?1 AND m.date_time >= ?2 AND m.date_time < ?3 ORDER BY m.date_time DESC", nativeQuery = true)
    List<Meal> findAllBetweenDates(int userId, LocalDateTime startDate, LocalDateTime endDate);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM meals m WHERE m.id = ?1 AND m.user_id = ?2", nativeQuery = true)
    int delete(int id, int userId);
}
