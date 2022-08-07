package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class LoginController extends AbstractController{
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        User user = DataBase.findUserById(userId);

        if (user == null) {
            httpResponse.addHeader("Set-Cookie","logined=false");
            httpResponse.sendRedirect("/user/login_failed.html");
            return;
        }
        if (user.getPassword().equals(password)) {
            httpResponse.addHeader("Set-Cookie","logined=true");
            httpResponse.sendRedirect("/index.html");
        }
        if (!user.getPassword().equals(password)) {
            httpResponse.addHeader("Set-Cookie","logined=false");
            httpResponse.sendRedirect("/user/login_failed.html");
        }
    }
}
