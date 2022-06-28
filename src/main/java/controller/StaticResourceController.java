package controller;

import java.net.URL;
import utils.FileIoUtils;
import webserver.Request;
import webserver.RequestLine;
import webserver.Response;

import java.nio.file.Files;
import java.nio.file.Paths;

public class StaticResourceController implements Controller {

    public Response service(Request request) {
        RequestLine requestLine = request.getRequestLine();
        String path = requestLine.getPath();
        String resourcePath = FileIoUtils.getResourcePath(requestLine.getPath());

        if (!isExists(resourcePath)) {
            throw new IllegalArgumentException("NotFound");
        }

        return new Response(request.getContentType(), path, null);
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
