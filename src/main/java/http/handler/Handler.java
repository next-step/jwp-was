package http.handler;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public interface Handler {
    byte[] getHttpBody(String path) throws IOException, URISyntaxException;
    HttpResponse getResponse(HttpRequest httpRequest) throws IOException, URISyntaxException;
    void doService(HttpRequest httpRequest, HttpResponse httpResponse);
    void writeResponse(DataOutputStream dos, HttpResponse httpResponse) throws IOException;
}
