package webserver.controller;

import cookie.Cookie;
import db.DataBase;
import model.User;
import webserver.http.model.HandlerAdapter;
import webserver.http.model.HttpRequest;
import webserver.http.model.Model;

import java.util.HashMap;
import java.util.Map;

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

    public Model retrieveUsers(HttpRequest httpRequest) {
        if (HandlerAdapter.accessiblePagesAfterLogin(httpRequest) && "logined=true".equals(httpRequest.getRequestHeaders().get("Cookie"))) {
            Map<String, Object> modelMap = new HashMap<>();
            modelMap.put("users", DataBase.findAll());
            return new Model("user/list", modelMap);
        }
        return new Model("/user/login.html", null);
    }
}
