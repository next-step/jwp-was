package db;

import com.google.common.collect.Maps;
import model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static User login(String userId, String password) throws FailedLoginException {
        User user = findUserById(userId);
        if (Objects.isNull(user)) {
            throw new FailedLoginException(userId);
        }

        if (!user.matchPassword(password)) {
            throw new FailedLoginException(password);
        }

        return user;
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static void clear() {
        users.clear();
    }
}
