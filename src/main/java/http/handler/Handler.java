package http.handler;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public interface Handler {
    String NEW_LINE_STRING = "\r\n";

    HttpResponse getResponse(HttpRequest httpRequest) throws IOException, URISyntaxException;
    void writeResponse(OutputStream out, HttpResponse httpResponse) throws IOException;
    byte[] getHttpBody(String path) throws IOException, URISyntaxException;
}
