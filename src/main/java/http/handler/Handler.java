package http.handler;

import http.common.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public interface Handler {
    String getPath();

    HttpStatus getHttpStatus();

    String getContentType();

    HttpResponse getHttpResponse(HttpRequest httpRequest) throws IOException, URISyntaxException;

    byte[] getHttpResponseBody(HttpRequest httpRequest) throws IOException, URISyntaxException;
}
