package webserver.http.handler;

import db.DataBase;
import model.User;
import webserver.HttpResponse;
import webserver.http.HttpRequest;
import webserver.http.RequestQuery;

public class CreateUserHandler implements Handler {

    @Override
    public void handle(final HttpRequest request,
                       final HttpResponse response) {
        final RequestQuery requestQuery = RequestQuery.of(request.getBody());
        
        final String userId = requestQuery.getString("userId");
        final String password = requestQuery.getString("password");
        final String name = requestQuery.getString("name");
        final String email = requestQuery.getString("email");

        final User user = new User(userId, password, name, email);
        DataBase.addUser(user);

        response.redirect("/index.html");
    }
}
