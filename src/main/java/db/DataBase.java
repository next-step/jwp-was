package db;

import com.google.common.collect.Maps;
import model.User;

import java.util.Collection;
import java.util.Map;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();
    static {
        DataBase.addUser(new User("koh", "123", "유식", "1@2"));
        DataBase.addUser(new User("koh1", "123", "유식", "1@2"));
        DataBase.addUser(new User("koh2", "123", "유식", "1@2"));
        DataBase.addUser(new User("koh3", "123", "유식", "1@2"));
        DataBase.addUser(new User("koh4", "123", "유식", "1@2"));
        DataBase.addUser(new User("koh5", "123", "유식", "1@2"));
        DataBase.addUser(new User("koh6", "123", "유식", "1@2"));
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
