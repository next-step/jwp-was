package user.service;

import db.DataBase;
import model.User;

import java.util.Collection;

public class RetrieveUserService {

    public static User retrieve(String userId) {
        return DataBase.findUserById(userId);
    }

    public static Collection<User> retrieveUsers() {
        return DataBase.findAll();
    }
}
