package controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class LoginController implements Controller {
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParameter("userId"));
        boolean isLoginSuccess = user.isSamePassword(request.getParameter("password"));
        String location = afterRedirect(isLoginSuccess);
        response.addHeader("Set-Cookie", "logined=" + isLoginSuccess + "; Path=/");
        response.sendRedirect(location);
    }

    private String afterRedirect(boolean isLoginSuccess) {
        String location = "/index.html";

        if (!isLoginSuccess) {
            location = "/user/login_failed.html";
        }
        return location;
    }
}
