package mvc.controller;

import http.HttpHeader;
import http.request.HttpRequest;
import http.request.protocol.Protocol;
import http.response.HttpResponse;
import http.response.HttpStatusCode;
import http.response.StatusLine;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

public class TemplateController extends AbstractController {

    private final static String TEMPLATE_PATH = "./templates";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        response.buildResponse(StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.OK),
                HttpHeader.from(Collections.singletonMap(HttpHeader.CONTENT_TYPE, "text/html;charset=utf-8")),
                FileIoUtils.loadFileFromClasspath(templatePath(request)));
    }

    private String templatePath(HttpRequest request) {
        return TEMPLATE_PATH + request.getPath();
    }
}
