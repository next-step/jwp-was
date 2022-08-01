package webserver.controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.user.UserFactory;

public class CreateUserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        final User user = UserFactory.from(request);
        logger.error("user : {}", user);

        DataBase.addUser(user);
        response.sendRedirect("/index.html");
    }
}
