package webserver.controller;

import utils.FileIoUtils;
import webserver.http.HttpHeaders;
import webserver.http.HttpSession;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatusCode;
import webserver.http.response.ResponseHeader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

public class TemplateController implements Controller {

    private static final String TEMPLATE_PATH = "./templates";

    @Override
    public boolean isMatch(HttpRequest request) {
        return request.matchMethod(HttpMethod.GET)
                && TemplateController.class.getClassLoader().getResource(templatePath(request)) != null;
    }

    @Override
    public HttpResponse execute(HttpRequest request, HttpSession session) throws IOException, URISyntaxException {
        return HttpResponse.of(
                HttpStatusCode.OK,
                ResponseHeader.from(Collections.singletonMap(HttpHeaders.CONTENT_TYPE, "text/html;charset=utf-8")),
                FileIoUtils.loadFileFromClasspath(templatePath(request))
        );
    }

    private String templatePath(HttpRequest request) {
        return TEMPLATE_PATH + request.path().toString();
    }
}
