package service;

import db.DataBase;
import model.User;
import webserver.request.Request;
import webserver.response.Response;
import webserver.response.ResponseFactory;

public class LoginService {
    private LoginService() {}

    public static Response login(Request request) {
        String userId = request.getBody("userId");
        String password = request.getBody("password");
        User user = DataBase.findUserById(userId);
        if (user == null || !user.getPassword().equals(password)) {
            return ResponseFactory.createRedirect("/login_failed.html")
                    .setCookie("loggedIn=false");
        }
        return ResponseFactory.createRedirect("/index.html")
                .setCookie("loggedIn=true");
    }
}
