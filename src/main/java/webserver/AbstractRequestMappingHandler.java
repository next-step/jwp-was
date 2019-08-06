package webserver;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractRequestMappingHandler implements RequestMappingHandler {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        if (request.isPostRequest()) {
            doPost(request, response);
        }

        if (request.isGetRequest()) {
            doGet(request, response);
        }
    }

    public void doPost(HttpRequest request, HttpResponse response) throws IOException {
    }

    public void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
    }
}
