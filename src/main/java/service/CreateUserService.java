package service;

import db.DataBase;
import model.User;
import webserver.request.Request;
import webserver.response.Response;
import webserver.response.ResponseFactory;

public class CreateUserService {
    private CreateUserService() {}

    public static Response createUser(Request request) {
        User user = new User(request.getBody("userId"),
                request.getBody("password"),
                request.getBody("name"),
                request.getBody("email")
        );
        DataBase.addUser(user);
        return ResponseFactory.createRedirect("/index.html");
    }
}
