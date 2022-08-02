package slipp.db;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.Maps;

import slipp.model.User;

public class DataBase {
    private final static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public static boolean exists(String userId) {
        return users.containsKey(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static void clearAll() {
        users.clear();
    }
}
