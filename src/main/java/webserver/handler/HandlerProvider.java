package webserver.handler;

import webserver.http.request.Request;

@FunctionalInterface
public interface HandlerProvider {

    Handler provide(final Request request);
}
