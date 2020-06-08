package controller;

import db.DataBase;
import http.request.FormData;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import webserver.controller.AbstractController;

public class UserLoginController extends AbstractController {

    private UserLoginController() {
    }

    private static class Singleton {
        private static final UserLoginController instance = new UserLoginController();
    }

    public static UserLoginController getInstance() {
        return Singleton.instance;
    }

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        FormData formData = new FormData(httpRequest.getBody());
        String userId = formData.getValue("userId");
        String password = formData.getValue("password");

        User user = DataBase.findUserById(userId);

        if (isAuthenticated(user, password)) {
            httpResponse.response302("/index.html");
            httpResponse.addCookie("logined", "true");
            httpResponse.addCookiePath("/");
        } else {
            httpResponse.response302("/user/login_failed.html");
            httpResponse.addCookie("logined", "false");
            httpResponse.addCookiePath("/");
        }
    }

    private boolean isAuthenticated(User user, String password) {
        if (user == null) {
            return false;
        }
        return user.checkSamePassword(password);
    }

}
