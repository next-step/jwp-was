package webserver.servlet;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface Servlet {

    String getName();

    void service(HttpRequest httpRequest, HttpResponse httpResponse);

}
