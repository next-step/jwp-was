package user.controller;

import db.DataBase;
import user.model.User;
import webserver.controller.AbstractController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;
import webserver.http.HttpSessionManager;

public class UserLoginController extends AbstractController {
    private static final String LOGINED_KEY = "logined";

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        final HttpSession httpSession = HttpSessionManager.getHttpSession(httpRequest);
        final User user = DataBase.findUserById(httpRequest.getAttribute("userId"));
        final String password = httpRequest.getAttribute("password");

        final HttpResponse httpResponse = login(httpSession, user, password);
        httpResponse.setSessionCookie(httpSession);
        return httpResponse;
    }

    private HttpResponse login(HttpSession httpSession, User user, String password) {
        if (loginSucceed(user, password)) {
            final HttpResponse httpResponse = HttpResponse.sendRedirect("/index.html");
            httpSession.setAttribute(LOGINED_KEY, true);
            return httpResponse;
        }

        final HttpResponse httpResponse = HttpResponse.sendRedirect("/user/login_failed.html");
        httpSession.setAttribute(LOGINED_KEY, false);
        return httpResponse;
    }

    private boolean loginSucceed(User user, String password) {
        return user != null && user.equalsPassword(password);
    }
}
