package webserver.controller;

import webserver.http.request.Request;
import webserver.http.response.Response;

@FunctionalInterface
public interface Controller {

    void service(final Request request, final Response response) throws Exception;
}
