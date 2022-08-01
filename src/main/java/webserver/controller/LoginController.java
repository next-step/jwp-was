package webserver.controller;

import webserver.Cookie;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.service.LoginService;

public class LoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        LoginService loginService = new LoginService(request, response);
        loginService.checkIdAndPassword(request.convertUserOfQueryParam());
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {

    }
}
