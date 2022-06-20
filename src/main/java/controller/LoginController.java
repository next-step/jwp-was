package controller;

import service.AuthService;
import webserver.request.Request;
import webserver.response.Response;
import webserver.response.ResponseFactory;

public class LoginController extends AbstractController {
    @Override
    Response doGet(Request request) {
        return login(
                request.getQuery("userId"),
                request.getQuery("password")
        );
    }

    @Override
    Response doPost(Request request) {
        return login(
                request.getBody("userId"),
                request.getBody("password")
        );
    }

    private Response login(String userId, String password) {
        boolean loggedIn = AuthService.login(userId, password);
        if (loggedIn) {
            return ResponseFactory.createRedirect("/index.html")
                    .setCookie("loggedIn=true");
        }
        return ResponseFactory.createRedirect("/login_failed.html")
                .setCookie("loggedIn=false");
    }
}
