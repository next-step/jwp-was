package controller;

import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class TemplateController implements Controller {
    private static final String TEMPLATE_PATH = "./templates";

    @Override
    public boolean match(HttpRequest request) {
        return request.isMatchMethod(HttpMethod.GET) && existsFile(request);
    }

    @Override
    public HttpResponse execute(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        return response.forward(addTemplatePath(request.getPath()), request.getPath());
    }

    private String addTemplatePath(String path) {
        return TEMPLATE_PATH + path;
    }

    private boolean existsFile(HttpRequest request) {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResource(addTemplatePath(request.getPath())) != null;
    }
}
