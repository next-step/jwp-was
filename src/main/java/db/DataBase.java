package db;

import exception.InvalidUserException;
import model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DataBase {
    private static final Map<String, User> users = new HashMap<>();

    public static void addUser(User user) {
        if (users.containsKey(user.getUserId())) {
            throw new InvalidUserException(user.getUserId());
        }
        users.put(user.getUserId(), user);
    }

    public static Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static void deleteAll() {
        users.clear();
    }
}
