package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.user.UserFactory;

public class CreateUserController extends AbstractController {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        final User user = UserFactory.from(request);

        DataBase.addUser(user);
        response.sendRedirect("/index.html");
    }
}
