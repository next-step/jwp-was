package mvc.controller;

import was.http.HttpRequest;
import was.http.HttpResponse;

public interface Controller {
    void service(HttpRequest request, HttpResponse response) throws Exception;
}
