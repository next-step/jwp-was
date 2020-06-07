package http.controller;

import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class ExceptionController extends AbstractController {
    private final HttpStatus httpStatus;

    public ExceptionController(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {

    }
}
