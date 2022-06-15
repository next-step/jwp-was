package service;

import db.DataBase;
import model.User;
import webserver.request.Request;
import webserver.response.Response;
import webserver.response.ResponseFactory;

public class CreateUserService {
    private CreateUserService() {}

    public static Response doGet(Request request) {
        User user = new User(request.getQuery("userId"),
                request.getQuery("password"),
                request.getQuery("name"),
                request.getQuery("email")
        );
        return createUser(user);
    }

    public static Response doPost(Request request) {
        User user = new User(request.getBody("userId"),
                request.getBody("password"),
                request.getBody("name"),
                request.getBody("email")
        );
        return createUser(user);
    }

    private static Response createUser(User user) {
        DataBase.addUser(user);
        return ResponseFactory.createRedirect("/index.html");
    }
}
