package domain.user;

import db.DataBase;
import model.User;
import webserver.handler.Handler;
import webserver.http.request.Request;
import webserver.http.request.RequestQuery;
import webserver.http.response.Response;

import java.util.Optional;

public class LoginHandler implements Handler {

    @Override
    public void handle(final Request request,
                       final Response response) throws Exception {
        final RequestQuery requestQuery = RequestQuery.of(request.getBody());
        
        final String userId = requestQuery.getString("userId");
        final String password = requestQuery.getString("password");

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
