package webserver.controller;

import webserver.http.request.Request;

@FunctionalInterface
public interface ControllerProvider {

    Controller provide(final Request request);
}
