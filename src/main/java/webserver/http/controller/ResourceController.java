package webserver.http.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceController extends AbstractController {

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) throws IOException {
        try {
            response.setBodyContentPath(request.getPath());
            response.responseOK();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
