package webserver.controller;

import utils.FileIoUtils;
import webserver.request.HttpMethod;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticController implements Controller {

    private static final String STATIC_PATH = "./static";

    @Override
    public boolean isMatch(HttpRequest request) {
        return request.matchMethod(HttpMethod.GET)
                && StaticController.class.getClassLoader().getResource(staticPath(request)) != null;
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws IOException, URISyntaxException {
        return HttpResponse.Builder.ok(FileIoUtils.loadFileFromClasspath(staticPath(request)))
                .contentType(String.format("text/%s;charset=utf-8", fileExtension(request)))
                .build();
    }

    private String fileExtension(HttpRequest request) {
        String path = request.path().toString();
        return path.substring(request.path().toString().lastIndexOf('.') + 1);
    }

    private String staticPath(HttpRequest request) {
        return STATIC_PATH + request.path().toString();
    }
}
