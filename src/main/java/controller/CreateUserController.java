package controller;

import model.User;
import service.UserService;
import webserver.request.Request;
import webserver.response.Response;

public class CreateUserController extends AbstractController {
    @Override
    Response doGet(Request request) {
        User user = new User(request.getQuery("userId"),
                request.getQuery("password"),
                request.getQuery("name"),
                request.getQuery("email")
        );
        return UserService.createUser(user);
    }

    @Override
    Response doPost(Request request) {
        User user = new User(request.getBody("userId"),
                request.getBody("password"),
                request.getBody("name"),
                request.getBody("email")
        );
        return UserService.createUser(user);
    }
}
