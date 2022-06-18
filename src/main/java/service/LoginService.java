package service;

import db.DataBase;
import model.User;
import webserver.response.Response;
import webserver.response.ResponseFactory;

public class LoginService {
    private LoginService() {}

    public static Response login(String userId, String password) {
        User user = DataBase.findUserById(userId);
        if (user == null || !user.getPassword().equals(password)) {
            return ResponseFactory.createRedirect("/login_failed.html")
                    .setCookie("loggedIn=false");
        }
        return ResponseFactory.createRedirect("/index.html")
                .setCookie("loggedIn=true");
    }
}
