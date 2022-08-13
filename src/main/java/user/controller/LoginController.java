package user.controller;

import user.service.RetrieveUserService;
import webserver.http.model.response.Cookie;
import model.User;
import webserver.http.model.request.HttpRequest;
import webserver.http.model.response.HttpResponse;

import java.io.IOException;

public class LoginController extends AbstractController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {

    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String userId = httpRequest.getRequestBody().getRequestBodyMap().get("userId");
        String password = httpRequest.getRequestBody().getRequestBodyMap().get("password");
        User user = RetrieveUserService.retrieve(userId);

        Cookie cookie = new Cookie("logined");
        String pathAfterLogin = pathAfterLogin(password, user, cookie);
        httpResponse.addCookie(cookie);

        httpResponse.movePage(pathAfterLogin);
    }

    private String pathAfterLogin(String password, User user, Cookie cookie) {
        if (user != null && user.getPassword().equals(password)) {
            cookie.setValue("true");
            return "/index.html";
        }
        cookie.setValue("false");
        return "/user/login_failed.html";
    }
}
