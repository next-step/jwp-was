package webserver.http.handler;

import webserver.http.HttpRequest;

@FunctionalInterface
public interface Condition {

    boolean support(final HttpRequest request);
}
