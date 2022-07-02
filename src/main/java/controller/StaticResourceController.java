package controller;

import utils.FileIoUtils;
import http.HttpRequest;
import http.RequestLine;
import http.HttpResponse;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StaticResourceController implements RequestController {

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        String path = requestLine.getPath();
        String resourcePath = FileIoUtils.getResourcePath(requestLine.getPath());

        if (!isExists(resourcePath)) {
            throw new IllegalArgumentException("NotFound");
        }

        return new HttpResponse(httpRequest.getContentType(), path, null);
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
