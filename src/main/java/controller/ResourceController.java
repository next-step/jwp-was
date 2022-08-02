package controller;

import model.HttpRequest;
import model.HttpResponse;
import model.HttpStatusCode;
import model.ResponseHeader;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceController implements Controller {
    private static final String TEMPLATE_PATH = "./templates";

    @Override
    public boolean matchHttpMethodAndPath(HttpRequest request) {
        return request.isMatchMethod(request.getHttpMethod()) &&
                ResourceController.class.getClassLoader().getResource(addTemplatePath(request.getPath())) != null;
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws IOException, URISyntaxException {
        String path = addTemplatePath(request.getPath());
        byte[] bytes = FileIoUtils.loadFileFromClasspath(path);
        return HttpResponse.of(HttpStatusCode.OK, ResponseHeader.textHtml(bytes.length), bytes);
    }

    private String addTemplatePath(String path) {
        return TEMPLATE_PATH + path;
    }
}
