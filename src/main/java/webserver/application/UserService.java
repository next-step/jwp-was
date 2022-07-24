package webserver.application;

import db.DataBase;
import model.User;
import webserver.domain.EntitySupplier;

public class UserService {

    public String createUser(EntitySupplier<User> request) {
        User user = request.supply();

        DataBase.addUser(user);

        return user.getUserId();
    }
}
