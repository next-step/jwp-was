package http.controller;

import http.requests.HttpRequest;
import http.responses.HttpResponse;

/**
 * controller for serving static resources like js, css, fonts
 */
public class StaticController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.addHeader("Content-Type", "text/css");
        response.render(request.getPath());
    }
}
