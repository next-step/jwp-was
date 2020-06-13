package http.handler;

import http.common.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Handler {
    String getPath();

    HttpStatus getHttpStatus();

    String getContentType();

    void handle(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;

    byte[] getHttpResponseBody(HttpRequest response) throws IOException, URISyntaxException;
}
