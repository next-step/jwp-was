package webserver.controller;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import webserver.persistence.Users;

public class UserCreateController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);

    private final Users users;

    public UserCreateController(Users users) {
        this.users = users;
    }

    @Override
    public HttpResponse run(HttpRequest request) {
        var body = request.getBody();
        var params = Arrays.stream(body.split("&"))
            .map(it -> it.split("="))
            .map(it -> Map.entry(it[0], it[1]))
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

        var user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        logger.debug("user {}", user);

        users.save(user.getUserId(), user);

        return new HttpResponse(HttpStatus.FOUND, Map.of("Location", "/templates/index.html"));
    }
}
