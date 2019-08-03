package webserver.http.handler;

import webserver.HttpResponse;
import webserver.http.HttpRequest;

@FunctionalInterface
public interface Handler {

    void handle(final HttpRequest request,
                final HttpResponse response) throws Exception;
}
