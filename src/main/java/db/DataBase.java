package db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import model.User;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    static {
        users.put("user1", new User("user1", "1234", "user1", "user1@test.com"));
        users.put("user2", new User("user2", "1234", "user2", "user2@test.com"));
        users.put("user3", new User("user3", "1234", "user3", "user3@test.com"));
    }

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
