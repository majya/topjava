package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mayia on 15.03.2016.
 */
public class InMemoryUserRepositoryImpl implements UserRepository {
    public static void main(String[] args) {
        UserRepository userRepository = new InMemoryUserRepositoryImpl();
        System.out.println(userRepository.getAll());
        userRepository.save(new User(4, "Juzik", null, null, 999, true, null));
        System.out.println(userRepository.getAll());
//        System.out.println(userRepository.delete(1));
        System.out.println(userRepository.getAll());
        System.out.println(userRepository.get(2));
        System.out.println(userRepository.getByEmail("bbb"));

    }

    private Map<Integer, User> repository = new ConcurrentHashMap<>();

    {
        save(new User(1, "Lala", "jjj", "jjj", 2000, true, new HashSet<>()));
        save(new User(2, "Alina", "llll", "hhhh", 1000, true, new HashSet<>()));
        save(new User(3, "Bo", "bbb", "aaaa", 1600, true, new HashSet<>()));
    }

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public boolean delete(int id) {
        if (repository.containsKey(id)) {
            repository.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = new ArrayList<>(repository.values());
        for (User user : users) {
            if (email.equalsIgnoreCase(user.getEmail()))
                return user;
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>(repository.values());
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return users;
    }
}
