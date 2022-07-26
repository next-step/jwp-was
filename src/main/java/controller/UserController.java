package controller;

import annotation.GetMapping;
import model.User;

public class UserController {

    @GetMapping(path = "/user")
    public String getUserTest() {
        return "getUserReturnValue";
    }

    @GetMapping(path = "/user/create")
    public User createUser(User user) {
        return user;
    }

}
