package service;

import db.DataBase;
import model.User;
import service.exceptions.UserNotFoundException;

import java.util.Optional;

public class UserService {

    public void signUp(User user) {
        DataBase.addUser(user);
    }

    public boolean authenticate(String userId, String password) throws UserNotFoundException {
        final User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("No such user in database!");
        }
        return Optional
                .ofNullable(user.getPassword())
                .orElse("").equals(password);
    }
}
