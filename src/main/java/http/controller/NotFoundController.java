package http.controller;

import http.requests.HttpRequest;
import http.responses.HttpResponse;

/**
 * Fallback controller
 */
public class NotFoundController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.render("/404.html");
    }
}
