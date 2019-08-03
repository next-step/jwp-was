package domain.user;

import db.DataBase;
import model.User;
import webserver.handler.Handler;
import webserver.http.request.Request;
import webserver.http.request.RequestQuery;
import webserver.http.response.Response;

public class CreateUserHandler implements Handler {

    @Override
    public void handle(final Request request,
                       final Response response) {
        final RequestQuery formData = RequestQuery.of(request.getBody());
        
        final String userId = formData.getString("userId");
        final String password = formData.getString("password");
        final String name = formData.getString("name");
        final String email = formData.getString("email");

        final User user = new User(userId, password, name, email);
        DataBase.addUser(user);

        response.redirect("/index.html");
    }
}
