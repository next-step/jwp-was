package webserver.http.handler;

import webserver.http.HttpRequest;

public interface HandlerProvider {

    boolean support(final HttpRequest request);
    Handler provide();
}
