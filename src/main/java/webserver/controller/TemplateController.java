package webserver.controller;

import utils.FileIoUtils;
import webserver.HttpHeaders;
import webserver.request.HttpMethod;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatusCode;
import webserver.response.ResponseHeader;

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
    public HttpResponse execute(HttpRequest request) throws IOException, URISyntaxException {
        return HttpResponse.of(HttpStatusCode.OK,
                ResponseHeader.from(Collections.singletonMap(HttpHeaders.CONTENT_TYPE, "text/html;charset=utf-8")),
                FileIoUtils.loadFileFromClasspath(templatePath(request))
        );
    }

    private String templatePath(HttpRequest request) {
        return TEMPLATE_PATH + request.path().toString();
    }
}
