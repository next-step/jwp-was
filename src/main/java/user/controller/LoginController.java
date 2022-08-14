package user.controller;

import exception.UnsupportedMethodException;
import user.service.RetrieveUserService;
import webserver.http.model.session.HttpSession;
import webserver.http.model.session.HttpSessions;
import webserver.http.model.response.Cookie;
import model.User;
import webserver.http.model.request.HttpRequest;
import webserver.http.model.response.HttpResponse;

import java.io.IOException;

public class LoginController extends AbstractController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        throw new UnsupportedMethodException();
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String userId = httpRequest.getRequestBody().getRequestBodyMap().get("userId");
        String password = httpRequest.getRequestBody().getRequestBodyMap().get("password");
        User user = RetrieveUserService.retrieve(userId);

        Cookie cookie = Cookie.createSessionIdToStore();
        String pathAfterLogin = pathAfterLogin(password, user, cookie);
        httpResponse.addCookie(cookie);

        httpResponse.movePage(pathAfterLogin);
    }

    private String pathAfterLogin(String password, User user, Cookie cookie) {
        HttpSession httpSession = new HttpSession();
        if (user != null && user.getPassword().equals(password)) {
            httpSession.setAttribute("login", "true");
            HttpSessions.setSingleHttpSession(httpSession);
            cookie.setValue(httpSession.getId());
            return "/index.html";
        }
        httpSession.setAttribute("login", "false");
        HttpSessions.setSingleHttpSession(httpSession);
        cookie.setValue(httpSession.getId());
        return "/user/login_failed.html";
    }
}
