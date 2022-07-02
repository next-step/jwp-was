package controller;

import webserver.http.HttpResponse;
import webserver.http.Request;
import utils.FileIoUtils;
import webserver.http.RequestController;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StaticResourceController implements RequestController {

    @Override
    public HttpResponse service(Request request) throws Exception {
        return doGet(request);
    }

    @Override
    public HttpResponse doGet(Request request) {
        String resourcePath = FileIoUtils.getResourcePath(request.getPath());

        if (!isExists(resourcePath)) {
            throw new IllegalArgumentException("NotFound");
        }

        return new HttpResponse(request.getHeader("Accept"), request.getPath(), null);
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
    public HttpResponse doPost(Request httpRequest) {
        return null;
    }

}
