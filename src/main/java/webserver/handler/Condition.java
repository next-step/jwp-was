package webserver.handler;

import webserver.http.request.HttpRequest;

@FunctionalInterface
public interface Condition {

    boolean support(final HttpRequest request);
}
