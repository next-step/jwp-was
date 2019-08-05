package domain.user;

import db.DataBase;
import model.User;
import webserver.handler.Handler;
import webserver.http.request.Request;
import webserver.http.response.Response;

import java.util.Optional;

public class LoginHandler implements Handler {

    @Override
    public void handle(final Request request,
                       final Response response) throws Exception {
        final String userId = request.getParameter("userId");
        final String password = request.getParameter("password");

        Optional.ofNullable(DataBase.findUserById(userId))
                .filter(user -> user.matchPassword(password))
                .map(this::successHandler)
                .orElseGet(this::failHandler)
                .handle(request, response);
    }

    private Handler failHandler() {
        return (request, response) -> {
            response.addHeader("Cookie", "logined=false");
            response.redirect("/user/login_failed.html");
        };
    }

    private Handler successHandler(final User user) {
        // TODO: Use user

        return (request, response) -> {
            response.addHeader("Set-Cookie", "logined=true; Path=/");
            response.redirect("/index.html");
        };
    }
}
