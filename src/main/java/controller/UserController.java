package controller;

import http.HttpRequest;
import http.HttpResponse;

public class UserController extends Controller {

    public UserController() {
        super("/user/create");
    }

    @Override
    public HttpResponse get(HttpRequest request) {
        return null;
    }

    @Override
    public HttpResponse post(HttpRequest request) {
        return null;
    }
}
