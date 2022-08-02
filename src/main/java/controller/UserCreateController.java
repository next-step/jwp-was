package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserCreateController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCreateController.class);

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        final User user = new User(
                httpRequest.getAttribute("userId"),
                httpRequest.getAttribute("password"),
                httpRequest.getAttribute("name"),
                httpRequest.getAttribute("email")
        );

        DataBase.addUser(user);

        return HttpResponse.sendRedirect("/index.html");
    }
}
