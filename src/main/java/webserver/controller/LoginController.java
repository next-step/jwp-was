package webserver.controller;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import db.DataBase;
import http.Cookie;
import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class LoginController extends AbstractController {

    @Override
    public HttpResponse doPost(HttpRequest request) {
        var body = request.getBody();
        var params = Arrays.stream(body.split("&"))
            .map(it -> it.split("="))
            .map(it -> Map.entry(it[0], it[1]))
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

        var userId = params.get("userId");

        var isLogined = DataBase.findUserById(userId)
            .filter(it -> it.canLogin(params.get("password")))
            .isPresent();

        var cookie = new Cookie("isLogined", String.valueOf(isLogined), Set.of("Path=/"));

        if (isLogined) {
            return new HttpResponse(HttpStatus.FOUND, Map.of("Location", "/index.html"), cookie);
        }
        return new HttpResponse(HttpStatus.FOUND, Map.of("Location", "/user/login_failed.html"), cookie);
    }
}
