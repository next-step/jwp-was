package controller;

import model.User;
import service.UserService;
import webserver.request.Request;
import webserver.response.Response;
import webserver.response.ResponseFactory;

public class CreateUserController extends AbstractController {
    @Override
    Response doGet(Request request) {
        return createUser(
                request.getQuery("userId"),
                request.getQuery("password"),
                request.getQuery("name"),
                request.getQuery("email")
        );
    }

    @Override
    Response doPost(Request request) {
        return createUser(
                request.getBody("userId"),
                request.getBody("password"),
                request.getBody("name"),
                request.getBody("email")
        );
    }

    private Response createUser(String userId, String password, String name, String email) {
        User user = new User(userId, password, name, email);
        UserService.save(user);
        return ResponseFactory.createRedirect("/index.html");
    }
}
