package webserver.servlet;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface Controller {

    void service(HttpRequest httpRequest, HttpResponse httpResponse);

}
