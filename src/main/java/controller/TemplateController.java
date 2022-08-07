package controller;

import model.*;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class TemplateController implements Controller {
    private static final String TEMPLATE_PATH = "./templates";

    @Override
    public boolean match(HttpRequest request) {
        return request.isMatchMethod(HttpMethod.GET) && existsFile(request);
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws IOException, URISyntaxException {
        String path = addTemplatePath(request.getPath());
        byte[] bytes = FileIoUtils.loadFileFromClasspath(path);
        return HttpResponse.ok(ResponseHeader.text(bytes.length, request.getPath()), bytes);
    }

    private String addTemplatePath(String path) {
        return TEMPLATE_PATH + path;
    }

    private boolean existsFile(HttpRequest request) {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResource(addTemplatePath(request.getPath())) != null;
    }
}
