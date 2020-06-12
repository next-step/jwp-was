package service;

import db.DataBase;
import model.User;

public class UserService {

    public static void save(User user) {
        DataBase.addUser(user);
    }

    public static boolean login(String id, String password) {
        User user = DataBase.findUserById(id);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
