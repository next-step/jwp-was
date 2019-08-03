package webserver.handler;

import webserver.http.request.Request;

public interface HandlerProvider {

    boolean support(final Request request);
    Handler provide();
}
