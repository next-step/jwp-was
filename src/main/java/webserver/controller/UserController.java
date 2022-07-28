package webserver.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import model.User;
import webserver.request.HttpRequest;
import webserver.request.QueryString;

public class UserController {
    User execute(HttpRequest httpRequest) {
        QueryString queryString = httpRequest.getQueryString();
        return new User(
                queryString.getParam("userId"),
                queryString.getParam("password"),
                queryString.getParam("name"),
                queryString.getParam("email")
        );
    }
}
