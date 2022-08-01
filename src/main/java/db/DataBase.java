package db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import model.User;

public class DataBase {
    private DataBase() {}

    private static final Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static boolean existsUser(String userId) {
        return users.containsKey(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static void clear() {
        users.clear();
    }
}
