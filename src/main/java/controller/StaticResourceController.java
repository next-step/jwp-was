package controller;

import webserver.Request;
import webserver.RequestLine;
import webserver.Response;

import java.nio.file.Files;
import java.nio.file.Paths;

public class StaticResourceController implements Controller {

    public Response serving(Request request) {
        RequestLine requestLine = request.getRequestLine();
        String resourcePath = getResourcePath(requestLine.getPath());

        if (!isExists(resourcePath)) {
            throw new IllegalArgumentException("NotFound");
        }

        return new Response(request.getContentType(), resourcePath);
    }

    private boolean isExists(String resourcePath) {
        return Files.exists(Paths.get("./webapp/" + resourcePath));
    }

    public String getResourcePath(String path) {
        String[] split = path.split("/");

        String filePath = path.replace(split[0], "");

        if (path.indexOf(".ico") != -1 || path.indexOf(".html") != -1) {
            return filePath;
        }

        return "static" + path;
    }
}
