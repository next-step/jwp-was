package webserver.handler;

import webserver.http.request.HttpRequest;

public interface HandlerProvider {

    boolean support(final HttpRequest request);
    Handler provide();
}
