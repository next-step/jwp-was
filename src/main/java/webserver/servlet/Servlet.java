package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface Servlet {

    String getRequestPath();

    HttpResponse service(HttpRequest request);
}
