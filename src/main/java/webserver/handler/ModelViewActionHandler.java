package webserver.handler;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface ModelViewActionHandler extends ActionHandler<ModelView> {

    ModelView actionHandle(HttpRequest httpRequest, HttpResponse httpResponse);
}
