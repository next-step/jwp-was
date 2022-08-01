package webserver.http.domain.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.domain.ContentType;
import webserver.http.domain.exception.ResourceNotFoundException;
import webserver.http.domain.request.Request;
import webserver.http.domain.response.Response;
import webserver.http.domain.response.StatusCode;

import java.util.List;

import static webserver.http.domain.ContentType.HTML;
import static webserver.http.domain.Headers.CONTENT_TYPE;

public class RequestProcessor {
    private static final Logger logger = LoggerFactory.getLogger(RequestProcessor.class);

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
            return getResponse(StatusCode.NOT_FOUND, "요청하신 리소스를 찾지 못했습니다. ;(");
        } catch (RuntimeException e) {
            logger.error("[internal error] - 요청값 처리중 에러 발생 = {}", e.getMessage(), e);
            return getResponse(StatusCode.INTERNAL_ERROR, "서버 내부에 오류가 발생했습니다. ;(");
        }
    }

    private Response getResponse(StatusCode statusCode, String text) {
        Response response = Response.from(statusCode);
        response.addHeader(CONTENT_TYPE, HTML.getHeader());
        response.addBody("<h1><meta charset=\"UTF-8\">" + text + "</h1>");
        return response;
    }
}
