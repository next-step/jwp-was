package db;

import static exception.ExceptionStrings.DUPLICATED_USER_ID;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import java.util.Optional;
import model.User;

public final class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    private DataBase() { }

    public static void addUser(User user) {
        if (users.containsKey(user.getUserId())) {
            throw new IllegalArgumentException(DUPLICATED_USER_ID);
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
