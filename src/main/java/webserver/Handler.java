package webserver;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface Handler {
    ModelAndView handle(HttpRequest httpRequest, HttpResponse httpResponse);
}
