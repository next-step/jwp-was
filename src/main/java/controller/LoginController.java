package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class LoginController extends AbstractController {

    public void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParameter("userId"));
        boolean isLogin = isLogin(request, user);
        response.addSession();

        if (isLogin) {
            response.forward("/index.html");
        } else {
            response.forward("/user/login_failed.html");
        }
    }

    private boolean isLogin(HttpRequest request, User user) {
        return user != null && user.equalsPassword(request.getParameter("password"));
    }

}
