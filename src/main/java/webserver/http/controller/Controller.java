package webserver.http.controller;

import webserver.http.request.Request;
import webserver.http.response.Response;

public interface Controller<T extends Request, E extends Response> {
    void service(T request, E response);
}
