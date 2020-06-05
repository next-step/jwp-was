package utils;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserData {
    private static Map<String, User> userMap = new HashMap<>();

    public static void save(User user) {
        userMap.put(user.getUserId(), user);
    }

    public static User getUser(String userId) {
        return userMap.get(userId);
    }
}
