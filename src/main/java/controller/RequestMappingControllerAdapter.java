package controller;

import http.HttpRequest;
import http.HttpStatus;
import http.MediaType;
import http.HttpResponse;

import java.io.IOException;

public abstract class RequestMappingControllerAdapter implements RequestController, RequestMapping {

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws IOException {
        return new HttpResponse(HttpStatus.NOT_FOUND, MediaType.TEXT_HTML_UTF8, "", null);
    }

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        return new HttpResponse(HttpStatus.NOT_FOUND, MediaType.TEXT_HTML_UTF8, "", null);
    }
}
