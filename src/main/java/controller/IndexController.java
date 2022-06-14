package controller;

import webserver.request.Request;
import webserver.response.Response;
import webserver.response.ResponseFactory;

public class IndexController implements Controller {
    @Override
    public Response service(Request request) {
        return ResponseFactory.createRedirect("/index.html");
    }
}
