package controller;

import annotation.GetMapping;
import annotation.PostMapping;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @GetMapping(path = "/user")
    public String getUserTest() {
        return "getUserReturnValue";
    }

    @GetMapping(path = "/user/create")
    public User createUserGet(User user) {
        DataBase.addUser(user);
        return user;
    }

    @PostMapping(path = "/user/create")
    public User createUserPost(User user) {
        DataBase.addUser(user);
        return user;
    }

}
