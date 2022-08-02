package controller;

import model.*;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class TemplateController implements Controller {
    private static final String TEMPLATE_PATH = "./templates";

    @Override
    public boolean matchHttpMethodAndPath(HttpRequest request) {
        return request.isMatchMethod(HttpMethod.GET) &&
                TemplateController.class.getClassLoader().getResource(addTemplatePath(request.getPath())) != null;
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws IOException, URISyntaxException {
        String path = addTemplatePath(request.getPath());
        byte[] bytes = FileIoUtils.loadFileFromClasspath(path);
        return HttpResponse.of(HttpStatusCode.OK, ResponseHeader.text(bytes.length, request.getPath()), bytes);
    }

    private String addTemplatePath(String path) {
        return TEMPLATE_PATH + path;
    }
}
