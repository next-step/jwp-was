package controller;

import webserver.request.Request;
import webserver.response.Response;
import webserver.response.ResponseFactory;

public class RedirectIndexController extends AbstractController {
    @Override
    Response doGet(Request request) {
        return ResponseFactory.createRedirect("/index.html");
    }

    @Override
    Response doPost(Request request) {
        return ResponseFactory.createNotImplemented();
    }
}
