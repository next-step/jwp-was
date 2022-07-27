package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class LoginController extends PostController {

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        if (isLoggedIn(userId, password)) {
            response.addCookie("logined", "true");
            response.addCookie("Path", "/");
            response.sendRedirect("/index.html");
            return;
        }

        response.addCookie("logined", "false");
        response.sendRedirect("/user/login_failed.html");
    }

    private boolean isLoggedIn(String userId, String password) {
        User user = DataBase.findUserById(userId);
        return user != null && password.equals(user.getPassword());
    }
}
