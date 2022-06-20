package webserver.http.controller;

import db.DataBase;
import model.User;
import webserver.http.Cookie;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.util.Optional;

public class LoginController implements Controller {

    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws IOException {
        final RequestBody requestBody = request.getBody();
        final String userId = requestBody.get("userId");
        final String password = requestBody.get("password");
        final Optional<User> user = DataBase.findUserById(userId);

        final boolean loginSuccess = user
                .map(it -> it.isPasswordMatched(password))
                .orElse(false);

        if (loginSuccess) {
            response.setCookie(new Cookie("logined", "true"));
            response.responseRedirect("/index.html");
            return;
        }

        response.setCookie(new Cookie("logined", "false"));
        response.responseRedirect("/user/login_failed.html");
    }
}
