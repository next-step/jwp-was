package service;

import db.DataBase;
import model.User;
import webserver.request.Request;
import webserver.response.Response;
import webserver.response.ResponseFactory;

public class LoginService {
    private LoginService() {}

    public static Response doGet(Request request) {
        return login(
                request.getQuery("userId"),
                request.getQuery("password")
        );
    }

    public static Response doPost(Request request) {
        return login(
                request.getBody("userId"),
                request.getBody("password")
        );
    }

    private static Response login(String userId, String password) {
        User user = DataBase.findUserById(userId);
        if (user == null || !user.getPassword().equals(password)) {
            return ResponseFactory.createRedirect("/login_failed.html")
                    .setCookie("loggedIn=false");
        }
        return ResponseFactory.createRedirect("/index.html")
                .setCookie("loggedIn=true");
    }
}
