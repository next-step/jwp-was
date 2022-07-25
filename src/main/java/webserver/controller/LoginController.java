package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class LoginController extends AbstractController {

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        if (isLoggedIn(userId, password)) {
            response.addHeader("Set-Cookie", "logined=true; Path=/");
            response.sendRedirect("/index.html");
            return;
        }

        response.addHeader("Set-Cookie", "logined=false");
        response.sendRedirect("/user/login_failed.html");
    }

    private boolean isLoggedIn(String userId, String password) {
        User user = DataBase.findUserById(userId);
        return user != null && password.equals(user.getPassword());
    }
}
