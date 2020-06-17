package http.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractController implements Controller {
    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws Exception {
        switch (request.getMethod()) {
            case GET:
                doGet(request, response);
                break;
            case POST:
                doPost(request, response);
                break;
            default:
                throw new IllegalArgumentException("HTTP Method not exist");
        }
    }

    public abstract void doGet(final HttpRequest request, final HttpResponse response) throws Exception;

    public abstract void doPost(final HttpRequest request, final HttpResponse response);
}
