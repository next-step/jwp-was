package controller;

import model.User;
import service.UserService;

public class UserController {

    public static void create(User user) {
        UserService.create(user);
    }

    public static boolean login(User user) {
        return UserService.login(user.getUserId(), user.getPassword());
    }

}
