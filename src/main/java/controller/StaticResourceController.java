package controller;

import http.HttpRequest;
import http.HttpResponse;
import utils.FileIoUtils;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StaticResourceController implements RequestController {

    @Override
    public HttpResponse service(HttpRequest httpRequest) throws Exception {
        return doGet(httpRequest);
    }

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        String resourcePath = FileIoUtils.getResourcePath(httpRequest.getPath());

        if (!isExists(resourcePath)) {
            throw new IllegalArgumentException("NotFound");
        }

        return new HttpResponse(httpRequest.getContentType(), httpRequest.getPath(), null);
    }

    private boolean isExists(String resourcePath) {
        URL resource = StaticResourceController.class.getClassLoader().getResource(resourcePath);

        if (resource == null) {
            return false;
        }

        String path = resource.getPath();
        return Files.exists(Paths.get(path));
    }

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        return null;
    }

}
