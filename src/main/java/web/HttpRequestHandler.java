package web;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;

public interface HttpRequestHandler {

    void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
