package http.controller;

import http.requests.HttpRequest;
import http.responses.HttpResponse;
import http.responses.HttpStatus;

/**
 * Fallback controller
 */
public class NotFoundController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND);
        response.render("/404.html");
    }
}
