package db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import model.User;

public class DataBase {
    private static final Map<String, User> USERS = Maps.newHashMap();

    public static void addUser(User user) {
        USERS.put(user.getUserId(), user);
    }

    public static User findUserById(final String userId) {
        return USERS.get(userId);
    }

    public static Collection<User> findAll() {
        return USERS.values();
    }
}
