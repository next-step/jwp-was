package service;

import db.DataBase;
import model.User;
import model.Users;

public class UserService {
    private UserService() {}

    public static Users findAll() {
        return Users.from(DataBase.findAll());
    }

    public static void save(User user) {
        DataBase.addUser(user);
    }
}
