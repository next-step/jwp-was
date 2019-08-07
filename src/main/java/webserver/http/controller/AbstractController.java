package webserver.http.controller;

import webserver.http.request.Request;
import webserver.http.response.Response;

abstract class AbstractController<T extends Request, E extends Response> implements Controller<T, E> {

    protected abstract void doGet(T request, E response);

    protected abstract void doPost(T request, E response);
}
