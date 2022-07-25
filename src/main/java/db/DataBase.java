package db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import model.User;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    static {
        // 계속 회원가입하기 번거로워 미리 데이터 만들어놓음.
        User user = new User("abc", "abc", "abc", "abc@google.com");
        users.put("abc", user);
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

    public static void clear() {
        users.clear();
    }
}
