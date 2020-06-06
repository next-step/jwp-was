package http.controller;

import http.requests.HttpRequest;
import http.responses.HttpResponse;

/**
 * html template controller
 */
public class TemplateController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.addHeader("Content-Type", "text/html;charset=utf-8");
        response.render(request.getPath());
    }
}
