package controller;

import model.User;
import webserver.http.HttpRequest;
import webserver.http.QueryString;

import java.util.Optional;

public class UserController {

    public static Optional createUser(HttpRequest httpRequest) {
        QueryString queryString = httpRequest.getUri().getQueryString();
        User user = new User(
                queryString.get("userId"),
                queryString.get("password"),
                queryString.get("name"),
                queryString.get("email")
        );

        return Optional.of(user);
    }
}
