package webserver.controller;

import webserver.Cookie;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.service.RequestService;

public class LoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        RequestService requestService = new RequestService(request);
        Cookie cookie = requestService.checkIdAndPassword(request.convertUserOfQueryParam());
        response.setCookie(cookie);
        response.makeLocationPath(requestService.getRedirectUrl(cookie));
        response.sendRedirect(request, response);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {

    }
}
