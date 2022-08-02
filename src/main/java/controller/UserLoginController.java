package controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserLoginController extends Controller {
    private static final String LOGINED_KEY = "logined";

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        final User user = DataBase.findUserById(httpRequest.getAttribute("userId"));
        if (loginFailed(user, httpRequest.getAttribute("password"))) {
            final HttpResponse httpResponse = HttpResponse.sendRedirect("/user/login_failed.html");
            httpResponse.setCookie("logined", false);
            return httpResponse;
        }

        final HttpResponse httpResponse = HttpResponse.sendRedirect("/index.html");
        httpResponse.setCookie(LOGINED_KEY, true);
        return httpResponse;
    }

    private boolean loginFailed(User user, String password) {
        return user == null || !user.equalsPassword(password);
    }
}
