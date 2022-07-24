package webserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.request.RequestLine;
import model.User;

public class UserCreateController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);

    @Override
    public void run(RequestLine requestLine) {
        var queryParams = requestLine.getQueryParams();
        var userId = queryParams.get("userId");
        var password = queryParams.get("password");
        var name = queryParams.get("name");
        var email = queryParams.get("email");

        var user = new User(userId, password, name, email);

        logger.debug("{}", user);
    }
}
