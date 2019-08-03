package webserver.http.handler;

import db.DataBase;
import model.User;
import webserver.HttpResponse;
import webserver.http.HttpRequest;
import webserver.http.RequestQuery;

import java.util.Optional;

public class LoginHandler implements Handler {

    private final Handler successHandler;
    private final Handler failHandler;

    public LoginHandler(final Handler successHandler,
                        final Handler failHandler) {
        this.successHandler = successHandler;
        this.failHandler = failHandler;
    }

    @Override
    public void handle(final HttpRequest request,
                       final HttpResponse response) throws Exception {
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
