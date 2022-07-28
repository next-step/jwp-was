package webserver.application;

import db.DataBase;
import model.User;
import model.dto.UserResponse;
import webserver.domain.EntitySupplier;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    public String createUser(EntitySupplier<User> request) {
        User user = request.supply();

        DataBase.addUser(user);

        return user.getUserId();
    }

    public boolean login(EntitySupplier<User> request) {
        User user = request.supply();
        User foundUser = DataBase.findUserById(user.getUserId());

        if (foundUser == null) {
            return false;
        }

        return foundUser.equalsCredential(user);
    }

    public List<UserResponse> findAllUsers() {
        return DataBase.findAll().stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());

    }
}
