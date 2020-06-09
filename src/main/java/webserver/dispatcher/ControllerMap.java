package webserver.dispatcher;

import http.controller.Controller;
import http.requests.HttpRequest;

import java.util.Map;

public class ControllerMap {

    private final Map<RequestBranch, Controller> controllerMap;

    public ControllerMap(Map<RequestBranch, Controller> controllerMap) {
        this.controllerMap = controllerMap;
    }

    public Controller getController(HttpRequest request) {
        final RequestBranch branch = new RequestBranch(request.getPath(), request.getMethod());
        return controllerMap.get(branch);
    }
}
