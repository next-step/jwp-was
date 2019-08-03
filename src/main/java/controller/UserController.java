package controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.RequestBody;

import java.util.Objects;
import java.util.Optional;

public class UserController {

    public static Optional createUser(HttpRequest httpRequest, HttpResponse httpResponse) {
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

    public static Optional login(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestBody requestBody = httpRequest.getRequestBody();
        String userId = requestBody.get("userId");
        String password = requestBody.get("password");

        User user = DataBase.findUserById(userId);
        if (Objects.isNull(user) || !user.getPassword().equals(password))
            return Optional.of("redirect:/user/login_failed.html");

        return Optional.of("redirect:/index.html");
    }
}
