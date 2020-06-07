package controller;

import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;
import http.StatusCode;
import sun.plugin.dom.exception.InvalidAccessException;

public abstract class AbstractController implements Controller {
    protected final String path;

    protected AbstractController(final String path) {
        this.path = path;
    }

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

    public String getPath() {
        return path;
    }
}
