package webserver.handler;

import webserver.http.request.Request;

@FunctionalInterface
public interface Condition {

    boolean support(final Request request);
}
