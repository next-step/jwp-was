package webserver.http.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;

public abstract class AbstractController implements Controller {
    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws IOException {
        switch (request.getMethod()) {
            case GET:
                this.doGet(request, response);
                return;

            case POST:
                this.doPost(request, response);
                return;

            default:
                response.responseMethodNotAllowed();
                return;
        }

    }

    protected void doGet(final HttpRequest request, final HttpResponse response) throws IOException {
        response.responseMethodNotAllowed();
    }

    protected void doPost(final HttpRequest request, final HttpResponse response) throws IOException {
        response.responseMethodNotAllowed();
    }
}
