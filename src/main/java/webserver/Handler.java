package webserver;

import webserver.http.Request;
import webserver.http.Response;

public interface Handler {
    ModelAndView handle(Request request, Response response);
}
