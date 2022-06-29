package controller;

import service.AuthService;
import webserver.common.HttpCookie;
import webserver.request.Request;
import webserver.response.Response;
import webserver.response.ResponseFactory;

public class LoginController extends AbstractController {
    @Override
    Response doGet(Request request) {
        return login(
                request.getQuery("userId"),
                request.getQuery("password"),
                request.getCookie()
        );
    }

    @Override
    Response doPost(Request request) {
        return login(
                request.getBody("userId"),
                request.getBody("password"),
                request.getCookie()
        );
    }

    private Response login(String userId, String password, HttpCookie httpCookie) {
        boolean loggedIn = AuthService.login(userId, password);
        httpCookie.getSession().setAttribute("loggedIn", loggedIn);
        return (
                loggedIn ? ResponseFactory.createRedirect("/index.html")
                        : ResponseFactory.createRedirect("/login_failed.html")
        ).setCookie(httpCookie);
    }
}
