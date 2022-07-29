package webserver.controller;

import webserver.Cookie;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.service.LoginService;

public class LoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        LoginService loginService = new LoginService(request);
        Cookie cookie = loginService.checkIdAndPassword(request.convertUserOfQueryParam());
        response.setCookie(cookie);
        response.makeLocationPath(loginService.getRedirectUrl(cookie));
        response.sendRedirect(request, response);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {

    }
}
