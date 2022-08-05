package webserver.controller;

import java.util.Map;
import java.util.Optional;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.config.TemplateEngine;

public class UserListController implements GetController {

    @Override
    public HttpResponse doGet(HttpRequest request) {
        Optional<String> cookie = request.getHttpSession()
            .getAttribute("isLogined")
            .map(Object::toString);

        if (cookie.isEmpty() || cookie.get().equals("false")) {
            return HttpResponse.found("/user/login.html");
        }

        var response = DataBase.findAll();

        String compile = TemplateEngine.getInstance()
            .compile("/user/list", Map.of("users", response));

        return HttpResponse.ok(compile);
    }
}
