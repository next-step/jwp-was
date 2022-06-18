package controller;

import service.LoginService;
import webserver.request.Request;
import webserver.response.Response;

public class LoginController extends AbstractController {
    @Override
    Response doGet(Request request) {
        return LoginService.login(
                request.getQuery("userId"),
                request.getQuery("password")
        );
    }

    @Override
    Response doPost(Request request) {
        return LoginService.login(
                request.getBody("userId"),
                request.getBody("password")
        );
    }
}
