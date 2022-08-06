package webserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class UserCreateController implements PostController {

    private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);

    @Override
    public HttpResponse doPost(HttpRequest request) {
        var params = request.getParameters();

        var user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        logger.debug("user {}", user);

        DataBase.addUser(user);

        return HttpResponse.found("/index.html");
    }
}
