package webserver;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractRequestMappingHandler implements RequestMappingHandler {

    @Override
    public void handleRequest(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        if (request.isPostRequest()) {
            postMapping(request, response);
        }

        if (request.isGetRequest()) {
            getMapping(request, response);
        }
    }

    private void postMapping(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        process(request, response);
    }

    private void getMapping(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        process(request, response);
    }

    protected abstract void process(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;
}
