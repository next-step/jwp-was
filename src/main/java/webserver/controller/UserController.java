package webserver.controller;

import cookie.Cookie;
import db.DataBase;
import model.User;
import webserver.http.model.HttpRequest;

public class UserController {

    public static UserController of() {
        return new UserController();
    }

    public String index(HttpRequest httpRequest) {
        return "/index.html";
    }

    public String createUserGet(HttpRequest httpRequest) {
        User user = new User(httpRequest.getQueryStrings());
        return "/index.html";
    }

    public String createUserPost(HttpRequest httpRequest) {
        User user = new User(httpRequest.getRequestBody());
        DataBase.addUser(user);
        return "/index.html";
    }

    public String login(HttpRequest httpRequest) {
        String userId = httpRequest.getRequestBody().getRequestBodyMap().get("userId");
        String password = httpRequest.getRequestBody().getRequestBodyMap().get("password");
        User user = DataBase.findUserById(userId);
        return pathAfterLogin(password, user);
    }

    private String pathAfterLogin(String password, User user) {
        Cookie cookie = new Cookie();
        if (user.getPassword().equals(password)) {
            cookie.setResponseLoginCookie(true);
            return "/index.html";
        }
        cookie.setResponseLoginCookie(false);
        return "/user/login_failed.html";
    }
}
