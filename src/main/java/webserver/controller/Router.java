package webserver.controller;

import java.util.Map;

import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.ControllerIdentity;
import webserver.controller.exception.ControllerNotFoundException;

public class Router {

    private final Map<ControllerIdentity, Controller> store;

    public Router(Map<ControllerIdentity, Controller> store) {
        this.store = store;
    }

    public HttpResponse handle(HttpRequest httpRequest) {
        if (httpRequest.isStaticFile()) {
            return HttpResponse.parseStaticFile(httpRequest.getUrl(), httpRequest.getFileExtension());
        }

        var controller = store.get(new ControllerIdentity(httpRequest.getUrl()));
        if (controller == null) {
            throw new ControllerNotFoundException(httpRequest.getUrl());
        }

        return controller.service(httpRequest);
    }
}
