package webserver.handler;

import webserver.http.response.HttpResponse;
import webserver.http.request.HttpRequest;

@FunctionalInterface
public interface Handler {

    void handle(final HttpRequest request,
                final HttpResponse response) throws Exception;
}
