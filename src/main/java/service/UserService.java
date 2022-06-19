package service;

import db.DataBase;
import model.User;
import model.Users;

public class UserService {
    private UserService() {}

    public static Users getUsers() {
        return Users.from(DataBase.findAll());
    }

    public static void createUser(User user) {
        DataBase.addUser(user);
    }
}
