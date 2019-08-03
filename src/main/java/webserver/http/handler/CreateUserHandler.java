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
