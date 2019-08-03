package webserver.handler;

import webserver.http.request.Request;
import webserver.http.response.Response;

@FunctionalInterface
public interface Handler {

    void handle(final Request request, final Response response) throws Exception;
}
