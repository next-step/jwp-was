package webserver.controller;

import db.DataBase;
import model.User;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;

public class LoginController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        try {
            if (isLogin(request)) {
                response.redirect("/index.html", "logined=true; Path=/");
            } else {
                response.redirect("/user/login_failed.html", "logined=false");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isLogin(HttpRequest httpRequest) {
        String userId = httpRequest.getBody().getParameter("userId");
        User user = DataBase.findUserById(userId);

        if (user == null) {
            return false;
        }

        String pw = httpRequest.getBody().getParameter("password");
        return user.getPassword().equals(pw);
    }
}
