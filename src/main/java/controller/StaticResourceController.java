package controller;

import utils.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.RequestAbstractController;
import webserver.http.RequestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StaticResourceController extends RequestAbstractController {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        doGet(httpRequest, httpResponse);
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String resourcePath = FileIoUtils.getResourcePath(httpRequest.getPath());

        if (!isExists(resourcePath)) {
            throw new IllegalArgumentException("NotFound");
        }

        httpResponse.addHeader("Content-Type", httpRequest.getHeader("Accept"));
        httpResponse.ok(FileIoUtils.loadFileFromClasspath(FileIoUtils.getResourcePath(httpRequest.getPath())));
    }

    private boolean isExists(String resourcePath) {
        URL resource = StaticResourceController.class.getClassLoader().getResource(resourcePath);

        if (resource == null) {
            return false;
        }

        String path = resource.getPath();
        return Files.exists(Paths.get(path));
    }

}
