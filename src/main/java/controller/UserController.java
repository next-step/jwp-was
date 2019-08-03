package controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.RequestBody;

import java.util.Optional;

public class UserController {

    public static Optional createUser(HttpRequest httpRequest) {
        RequestBody requestBody = httpRequest.getRequestBody();
        User user = new User(
                requestBody.get("userId"),
                requestBody.get("password"),
                requestBody.get("name"),
                requestBody.get("email")
        );

        DataBase.addUser(user);
        return Optional.of("redirect:/index.html");
    }
}
