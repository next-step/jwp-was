package db;

import com.google.common.collect.Maps;
import model.User;

import java.util.Collection;
import java.util.Map;

public class DataBase {

    private DataBase() {
        throw new AssertionError("'Assert' can not be instanced");
    }

    private static final Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.getOrDefault(userId, null);
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static void deleteUser(User user) {
        users.remove(user.getUserId());
    }
}
