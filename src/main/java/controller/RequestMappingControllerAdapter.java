package controller;

import webserver.HttpStatus;
import webserver.MediaType;
import webserver.Request;
import webserver.Response;

import java.io.IOException;

public abstract class RequestMappingControllerAdapter implements RequestController, RequestMapping {

    @Override
    public Response doGet(Request request) throws IOException {
        return new Response(HttpStatus.NOT_FOUND, MediaType.TEXT_HTML_UTF8, "", null);
    }

    @Override
    public Response doPost(Request request) {
        return new Response(HttpStatus.NOT_FOUND, MediaType.TEXT_HTML_UTF8, "", null);
    }
}
