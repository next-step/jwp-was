package http.controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class UserLoginController extends AbstractController {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.notExistApi();;
    }

    @Override
    public void doPost(final HttpRequest request, final HttpResponse response) {
        final User user = DataBase.findUserById(request.getParameter("userId"));
        if (user == null
                || !user.getPassword().equals(request.getParameter("password"))) {
            response.addCookie("login", "false");
            response.sendRedirect("/user/login_failed.html");
            return;
        }
        response.addCookie("login", "true");
        response.sendRedirect("/index.html");

    }
}
