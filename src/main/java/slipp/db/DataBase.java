package slipp.db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import slipp.model.User;

public class DataBase {
    private final static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static void clearAll() {
        users.clear();
    }
}
