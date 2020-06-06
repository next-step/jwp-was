package utils;

import model.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserData {
    private static Map<String, User> userMap = new HashMap<>();

    public static void save(User user) {
        userMap.put(user.getUserId(), user);
    }

    public static User getUser(String userId) {
        return userMap.get(userId);
    }

    public static List<User> findAll() {
        return userMap.values().stream()
                .collect(Collectors.toList());
    }
}
