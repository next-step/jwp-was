package webserver.controller;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class UserCreateController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);

    @Override
    public HttpResponse doPost(HttpRequest request) {
        var body = request.getBody();
        var params = Arrays.stream(body.split("&"))
            .map(it -> it.split("="))
            .map(it -> Map.entry(it[0], it[1]))
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

        var user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        logger.debug("user {}", user);

        DataBase.addUser(user);

        return HttpResponse.found("/index.html");
    }
}
