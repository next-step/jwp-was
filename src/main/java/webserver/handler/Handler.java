package webserver.handler;

import service.Service;
import webserver.request.RequestLine;

import java.util.List;

public abstract class Handler {

    public Service find(RequestLine requestLine) {
        return getServices().stream()
                .filter(service -> service.canServe(requestLine))
                .findFirst()
                .orElse(null);
    }

    public abstract List<Service> getServices();
}
