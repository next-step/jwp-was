package service;

import db.DataBase;
import model.User;

public class UserService {

    public static void create(User user) {
        DataBase.addUser(user);
    }
}
