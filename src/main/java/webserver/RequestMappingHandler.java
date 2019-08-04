package webserver;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface RequestMappingHandler {
    void handleRequest(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;
}
