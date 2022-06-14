package controller;

import webserver.request.Request;
import webserver.response.HttpStatus;
import webserver.response.Response;
import webserver.response.ResponseHeader;

public class IndexController implements Controller {
    @Override
    public Response service(Request request) {
        ResponseHeader responseHeader = new ResponseHeader(HttpStatus.FOUND)
                .setLocation("/index.html");
        return new Response(responseHeader);
    }
}
