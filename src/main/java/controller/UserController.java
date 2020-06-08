package controller;

import model.User;
import service.UserService;

public class UserController {

    public static void create(User user) {
        UserService.create(user);

    }
}
