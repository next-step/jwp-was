package db;

import com.google.common.collect.Maps;
import model.User;
import webserver.http.request.HttpRequestParams;

import java.util.*;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static User findById(HttpRequestParams requestParams) {
        String userId = requestParams.findByKey("userId");
        return Optional.ofNullable(users.get(userId))
                .orElse(User.empty());
    }

    public static List<User> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(users.values()));
    }
}
