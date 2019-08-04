package webserver.handler;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface Handler {

    HttpResponse doHandle(HttpRequest httpRequest) throws Exception;
    boolean canHandle(HttpRequest httpRequest);
}