package service;

import model.User;

import java.util.Map;

import static db.DataBase.addUser;
import static db.DataBase.findUserById;

public class UserService {
    public User createUser(Map<String, String> queryString) {
        User user = new User(queryString);

        addUser(user);

        return findUserById(user.getUserId());
    }

    public User findById(String id) {
        return findUserById(id);
    }
}
