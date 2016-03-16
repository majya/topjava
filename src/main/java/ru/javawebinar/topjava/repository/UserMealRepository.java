package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface UserMealRepository {
    UserMeal save(int userId, UserMeal userMeal);

    // false if not found or belongs to another user
    boolean delete(int userId, int userMealId);

    // null if not found or belongs to another user
    UserMeal get(int userId, int userMealId);

    // gets list of meal for the user
    List<UserMeal> getAll(int userId);
}
