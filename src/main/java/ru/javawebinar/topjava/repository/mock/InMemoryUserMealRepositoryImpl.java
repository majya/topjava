package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.09.2015.
 */

@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {

    private Map<Integer, Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
//        UserMealsUtil.MEAL_LIST.forEach(this::save);
        UserRepository userRepository = new InMemoryUserRepositoryImpl();
        List<User> userList = userRepository.getAll();

        for (User anUserList : userList) {
            for (UserMeal userMeal : UserMealsUtil.MEAL_LIST) {
                UserMeal meal = new UserMeal(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories());
                save(anUserList.getId(), meal);
            }
        }
    }

    @Override
    public UserMeal save(int userId, UserMeal userMeal) {
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }
        if (repository.isEmpty() || !repository.containsKey(userId) || repository.get(userId).isEmpty()) {
            Map<Integer, UserMeal> userMealMap = new ConcurrentHashMap<>();
            userMealMap.put(userMeal.getId(), userMeal);
            repository.put(userId, userMealMap);
        } else {
            Map<Integer, UserMeal> temp = repository.get(userId);
            temp.put(userMeal.getId(), userMeal);

            repository.put(userId, temp);
        }
        return userMeal;
    }

    @Override
    public boolean delete(int userId, int userMealId) {
        if (repository.containsKey(userId)) {
            if (repository.get(userId).containsKey(userMealId)) {
                repository.get(userId).remove(userMealId);
                return true;
            }
        }

        return false;
    }

    @Override
    public UserMeal get(int userId, int userMealId) {
        return repository.get(userId).get(userMealId);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        List<UserMeal> userMeals = new ArrayList<>(repository.get(userId).values());
        Collections.sort(userMeals, new Comparator<UserMeal>() {
            @Override
            public int compare(UserMeal o1, UserMeal o2) {
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });
        return userMeals;
    }
}

