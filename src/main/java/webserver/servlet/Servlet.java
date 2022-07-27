package webserver.servlet;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

@FunctionalInterface
public interface Servlet {

    void serve(HttpRequest httpRequest, HttpResponse httpResponse);

}
