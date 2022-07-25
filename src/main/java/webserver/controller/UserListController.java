package webserver.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.persistence.Users;

public class UserListController implements Controller {

    private final Users users;

    public UserListController(Users users) {
        this.users = users;
    }

    @Override
    public HttpResponse run(HttpRequest request) {
        // request.getCookie()
            return null;
    }
}
