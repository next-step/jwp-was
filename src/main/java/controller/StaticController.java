package controller;

import model.*;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticController implements Controller {

    private static final String STATIC_PATH = "./static";

    @Override
    public boolean matchHttpMethodAndPath(HttpRequest request) {
        return request.isMatchMethod(HttpMethod.GET) &&
                StaticController.class.getClassLoader().getResource(addStaticPath(request.getPath())) != null;
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws IOException, URISyntaxException {
        String path = addStaticPath(request.getPath());
        byte[] bytes = FileIoUtils.loadFileFromClasspath(path);
        return HttpResponse.of(HttpStatusCode.OK, ResponseHeader.text(bytes.length, request.getPath()), bytes);
    }

    private String addStaticPath(String path) {
        return STATIC_PATH + path;
    }
}
