package service;

import com.google.common.collect.Lists;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void add(final User user) {

        if (DataBase.findUserById(user.getUserId()) != null) {
            throw new IllegalArgumentException("user can not be duplicated");
        }

        logger.debug("add user into user registry, user: {}", user);
        DataBase.addUser(user);
    }

    public User get(final String userId) {
        return DataBase.findUserById(userId);
    }

    public List<User> getAll() {
        logger.debug("get all user, {}", DataBase.findAll());
        return Lists.newArrayList(DataBase.findAll());
    }
}
