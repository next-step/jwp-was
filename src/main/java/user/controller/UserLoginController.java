package user.controller;

import db.DataBase;
import user.model.User;
import webserver.controller.AbstractController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserLoginController extends AbstractController {
    private static final String LOGINED_KEY = "logined";

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        final User user = DataBase.findUserById(httpRequest.getAttribute("userId"));
        final String password = httpRequest.getAttribute("password");
        if (loginSucceed(user, password)) {
            final HttpResponse httpResponse = HttpResponse.sendRedirect("/index.html");
            httpResponse.setCookie(LOGINED_KEY, true);
            return httpResponse;
        }

        final HttpResponse httpResponse = HttpResponse.sendRedirect("/user/login_failed.html");
        httpResponse.setCookie(LOGINED_KEY, false);
        return httpResponse;
    }

    private boolean loginSucceed(User user, String password) {
        return user != null && user.equalsPassword(password);
    }
}
