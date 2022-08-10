package webserver.controller;

import cookie.Cookie;
import db.DataBase;
import model.User;
import webserver.http.model.Model;
import webserver.http.model.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class UserController {
    private static final UserController userController = new UserController();

    private UserController() {

    }

    public static UserController of() {
        return userController;
    }

    public String index(HttpRequest httpRequest) {
        return "/index.html";
    }

    public String createUserGet(HttpRequest httpRequest) {
        User user = new User(httpRequest.getQueryStrings().getQueryStringMap());
        return "/index.html";
    }

    public String createUserPost(HttpRequest httpRequest) {
        User user = new User(httpRequest.getRequestBody().getRequestBodyMap());
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
            cookie.setCookie("logined=true");
            return "/index.html";
        }
        cookie.setCookie("logined=false");
        return "/user/login_failed.html";
    }

    public Model retrieveUsers(HttpRequest httpRequest) {
        if (ControllerEnum.accessiblePagesAfterLogin(httpRequest) && "logined=true".equals(httpRequest.getRequestHeaders().get("Cookie"))) {
            Map<String, Object> modelMap = new HashMap<>();
            modelMap.put("users", DataBase.findAll());
            return new Model("user/list", modelMap);
        }
        return new Model("/user/login.html", null);
    }
}
