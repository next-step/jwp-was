package model;

import java.util.HashMap;
import java.util.Map;

public class Cookie {

    private static Map<String, User> data = new HashMap<>();

    public static void add(User user) {
        data.put(user.getUserId(), user);
    }
}
