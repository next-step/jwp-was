package controller;

import annotation.GetMapping;
import annotation.PostMapping;
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
        logger.info("GET : user created >>> {}}", user.toString());
        return user;
    }

    @PostMapping(path = "/user/create")
    public User createUserPost(User user) {
        logger.info("POST : user created >>> {}}", user.toString());
        return user;
    }

}
