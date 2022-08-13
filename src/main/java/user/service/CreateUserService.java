package user.service;

import db.DataBase;
import model.User;

public class CreateUserService {

    public static void create(User user) {
        DataBase.addUser(user);
    }
}
