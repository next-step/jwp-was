package webserver.handler;

import service.Controller;
import webserver.request.HttpRequest;

import java.util.List;

public abstract class Handler {

    public Controller find(HttpRequest httpRequest) {
        return getServices().stream()
                .filter(service -> service.canServe(httpRequest))
                .findFirst()
                .orElse(null);
    }

    public abstract List<Controller> getServices();
}
