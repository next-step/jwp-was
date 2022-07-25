package webserver.controller;

import utils.FileIoUtils;
import webserver.request.HttpMethod;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class TemplateController implements Controller {

    private static final String TEMPLATE_PATH = "./templates";

    @Override
    public boolean isMatch(HttpRequest request) {
        return request.matchMethod(HttpMethod.GET)
                && TemplateController.class.getClassLoader().getResource(templatePath(request)) != null;
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws IOException, URISyntaxException {
        return HttpResponse.Builder.ok(FileIoUtils.loadFileFromClasspath(templatePath(request)))
                .contentType("text/html;charset=utf-8")
                .build();
    }

    private String templatePath(HttpRequest request) {
        return TEMPLATE_PATH + request.path().toString();
    }
}
