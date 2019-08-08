package webserver.http.controller;

import webserver.http.HttpStatus;
import webserver.http.request.Request;
import webserver.http.response.Response;

import java.io.IOException;

abstract class AbstractController<T extends Request, E extends Response> implements Controller<T, E> {

    protected void doGet(T request, E response) throws IOException {
        response.error(HttpStatus.METHOD_NOT_ALLOWED);
    }

    protected void doPost(T request, E response) throws IOException {
        response.error(HttpStatus.METHOD_NOT_ALLOWED);
    }
}
