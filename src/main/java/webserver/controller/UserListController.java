package webserver.controller;

import java.util.Optional;

import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.config.TemplateEngine;
import webserver.persistence.Users;

public class UserListController implements Controller {

    private final Users users;

    public UserListController(Users users) {
        this.users = users;
    }

    @Override
    public HttpResponse run(HttpRequest request) {
        Optional<String> cookie = request.getCookie("isLogined");
        if (cookie.isEmpty() || cookie.get().equals("false")) {
            return HttpResponse.found("/login.html");
        }

        var response = users.findAll();

        String compile = TemplateEngine.getInstance()
            .compile("/user/list", response);

        return HttpResponse.ok(compile);
    }
}
