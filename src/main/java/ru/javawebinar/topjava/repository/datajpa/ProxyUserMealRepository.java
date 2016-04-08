package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Mayia on 07.04.2016.
 */

@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

    @Override
    @Transactional
    UserMeal save(UserMeal userMeal);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserMeal m WHERE m.id=:id AND m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Modifying
    @Query("SELECT m FROM UserMeal m WHERE m.id=:id AND m.user.id=:userId")
    List<UserMeal> findOne(@Param("id") int id, @Param("userId") int userId);

    @Modifying
    @Query("SELECT m FROM UserMeal m WHERE m.user.id=:userId  ORDER BY m.dateTime DESC")
    List<UserMeal> findAll(@Param("userId") int userId);

    @Modifying
    @Query("SELECT m FROM UserMeal m WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime DESC")
    List<UserMeal> getBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);

    @Query("SELECT m FROM UserMeal m LEFT JOIN FETCH m.user WHERE m.id=:id AND m.user.id=:userId")
    List<UserMeal>getMealWithUser(@Param("id")int id, @Param("userId")int userId);

}
