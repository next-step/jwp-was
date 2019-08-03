package domain.user;

import db.DataBase;
import webserver.handler.Handler;
import webserver.http.request.Request;
import webserver.http.request.RequestQuery;
import webserver.http.response.Response;

import java.util.Optional;

public class LoginHandler implements Handler {

    private final Handler successHandler;
    private final Handler failHandler;

    LoginHandler(final Handler successHandler,
                 final Handler failHandler) {
        this.successHandler = successHandler;
        this.failHandler = failHandler;
    }

    @Override
    public void handle(final Request request,
                       final Response response) throws Exception {
        final RequestQuery requestQuery = RequestQuery.of(request.getBody());
        
        final String userId = requestQuery.getString("userId");
        final String password = requestQuery.getString("password");

        Optional.ofNullable(DataBase.findUserById(userId))
                .filter(user -> user.matchPassword(password))
                .map(user -> successHandler)
                .orElse(failHandler)
                .handle(request, response);
    }
}
