package webserver.controller;

import cookie.Cookie;
import db.DataBase;
import model.User;
import webserver.http.model.request.HttpRequest;
import webserver.http.model.response.HttpResponse;

import java.io.IOException;

public class LoginController extends AbstractController {

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String userId = httpRequest.getRequestBody().getRequestBodyMap().get("userId");
        String password = httpRequest.getRequestBody().getRequestBodyMap().get("password");
        User user = DataBase.findUserById(userId);
        String pathAfterLogin = pathAfterLogin(password, user);
        httpResponse.moveNotStaticResourcePage(httpResponse, pathAfterLogin);
    }

    private String pathAfterLogin(String password, User user) {
        Cookie cookie = new Cookie();
        if (user != null && user.getPassword().equals(password)) {
            cookie.setCookie("logined=true");
            return "/index.html";
        }
        cookie.setCookie("logined=false");
        return "/user/login_failed.html";
    }
}
