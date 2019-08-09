package db;

import com.google.common.collect.Maps;
import domain.user.User;

import java.util.Collection;
import java.util.Map;

public final class DataBase {

    private static final Map<String, User> users = Maps.newHashMap();

    private DataBase() { }

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
