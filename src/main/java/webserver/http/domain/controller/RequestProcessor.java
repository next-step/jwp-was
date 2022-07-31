package webserver.http.domain.controller;

import webserver.http.domain.exception.ResourceNotFoundException;
import webserver.http.domain.request.Request;
import webserver.http.domain.response.Response;
import webserver.http.domain.response.StatusCode;

import java.util.List;

public class RequestProcessor {
    private final List<Controller> controllers;

    public RequestProcessor(List<Controller> controllers) {
        this.controllers = controllers;
    }

    public Response process(Request request) {
        try {
            Controller handler = controllers.stream()
                    .filter(controller -> controller.requires(request))
                    .findAny()
                    .orElseThrow(() -> new ResourceNotFoundException("요청하신 리소스가 존재하지 않습니다."));

            return handler.handle(request);
        } catch (ResourceNotFoundException e) {
            Response resourceNotFound = Response.from(StatusCode.NOT_FOUND);
            resourceNotFound.addHeader("Content-Type", "text/html");
            resourceNotFound.addBody("<h1><meta charset=\"UTF-8\">요청하신 리소스를 찾지 못했습니다. ;(</h1>");
            return resourceNotFound;
        } catch (RuntimeException e) {
            Response resourceNotFound = Response.from(StatusCode.INTERNAL_ERROR);
            resourceNotFound.addHeader("Content-Type", "text/html");
            resourceNotFound.addBody("<h1><meta charset=\"UTF-8\">서버 내부에 오류가 발생했습니다. ;(</h1>");
            return resourceNotFound;
        }
    }
}
