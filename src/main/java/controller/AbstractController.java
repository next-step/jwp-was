package controller;

import exception.InvalidAccessException;
import http.StatusCode;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractController implements Controller {
    protected final String path;

    protected AbstractController(final String path) {
        this.path = path;
    }

    @Override
    public void process(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        if (httpRequest.getMethod() == HttpMethod.GET) {
            get(httpRequest, httpResponse);
            return;
        }

        post(httpRequest, httpResponse);
    }

    protected void get(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        notFound(httpResponse);
    }

    protected void post(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        notFound(httpResponse);
    }

    private void notFound(final HttpResponse httpResponse) {
        httpResponse.updateStatus(StatusCode.NOT_FOUND);
        throw new InvalidAccessException("404 NOT FOUND");
    }

    @Override
    public String getPath() {
        return path;
    }
}
