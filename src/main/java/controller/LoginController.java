package controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class LoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws Exception {
        User user = DataBase.findUserById(request.getParameter("userId"));
        boolean isLoginSuccess = login(user, request.getParameter("password"));
        String location = afterRedirect(isLoginSuccess);
        response.addHeader("Set-Cookie", "logined=" + isLoginSuccess + "; Path=/");
        response.sendRedirect(location);
    }

    private boolean login(User user, String userPassword) {
        if (user == null) {
            return false;
        }
        return user.isSamePassword(userPassword);
    }

    private String afterRedirect(boolean isLoginSuccess) {
        String location = "/index.html";

        if (!isLoginSuccess) {
            location = "/user/login_failed.html";
        }
        return location;
    }
}
