package webserver.controller;

import http.request.Protocol;
import utils.FileIoUtils;
import webserver.HttpHeader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatusCode;
import webserver.response.StatusLine;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

public class TemplateController implements Controller {

    private final static String TEMPLATE_PATH = "./templates";

    @Override
    public HttpResponse service(HttpRequest request) throws IOException, URISyntaxException {
        return HttpResponse.of(
                StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.OK),
                HttpHeader.from(Collections.singletonMap(HttpHeader.CONTENT_TYPE, "text/html;charset=utf-8")),
                FileIoUtils.loadFileFromClasspath(templatePath(request))
        );
    }

    private String templatePath(HttpRequest request) {
        return TEMPLATE_PATH + request.getPath();
    }
}
