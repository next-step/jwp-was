package service;

import db.DataBase;
import model.User;

public class AuthService {
    private AuthService() {}

    public static boolean login(String userId, String password) {
        User user = DataBase.findUserById(userId);
        return user != null && user.matchPassword(password);
    }
}
