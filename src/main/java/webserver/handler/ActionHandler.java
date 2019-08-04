package webserver.handler;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface ActionHandler<T> {

    T actionHandle(HttpRequest httpRequest, HttpResponse httpResponse);
}
