package webserver;

import webserver.http.HttpRequest;
import webserver.http.Response;

public interface Handler {
    ModelAndView handle(HttpRequest httpRequest, Response response);
}
