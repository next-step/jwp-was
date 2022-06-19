package service;

import db.DataBase;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserService {
    private UserService() {}

    public static List<User> getUserList() {
        return new ArrayList<>(DataBase.findAll());
    }

    public static void createUser(User user) {
        DataBase.addUser(user);
    }
}
