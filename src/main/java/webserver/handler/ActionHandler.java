package webserver.handler;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface ActionHandler {

    void actionHandle(HttpRequest httpRequest, HttpResponse httpResponse);
}
