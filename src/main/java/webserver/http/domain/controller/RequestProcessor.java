package webserver.http.domain.controller;

import webserver.http.domain.exception.ResourceNotFoundException;
import webserver.http.domain.request.Request;
import webserver.http.domain.response.Response;

import java.util.List;

public class RequestProcessor {

    private final List<Controller> controllers;

    public RequestProcessor(List<Controller> controllers) {
        this.controllers = controllers;
    }

    public Response process(Request request) {
        Controller handler = controllers.stream()
                .filter(controller -> controller.requires(request))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("요청하신 리소스가 존재하지 않습니다."));

        return handler.handle(request);
    }
}
