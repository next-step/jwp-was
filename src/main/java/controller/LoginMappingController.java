package controller;

import db.DataBase;
import model.User;
import webserver.http.*;

public class LoginMappingController extends RequestMappingControllerAdapter {
    @Override
    public boolean checkUrl(String url) {
        return "/user/login".equals(url);
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        login(httpRequest, httpResponse);
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        login(httpRequest, httpResponse);
    }

    private void login(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = getUserFromRequest(httpRequest);

        User userById = DataBase.findUserById(user.getUserId());

        if (userById == null || !userById.checkPassword(user.getPassword())) {
            httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
            httpResponse.redirect(HttpStatus.BAD_REQUEST, "/user/login_failed.html");
            return;
        }

        httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
        httpResponse.redirect("/index.html");
    }

    private User getUserFromRequest(HttpRequest httpRequest) {
        return new User(
                httpRequest.getParameter("userId"),
                httpRequest.getParameter("password"),
                httpRequest.getParameter("name"),
                httpRequest.getParameter("email")
        );
    }
}
