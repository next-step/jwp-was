package webserver.controller;

import utils.IOUtils;
import webserver.ContentType;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticResourceController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        String path = request.getPath();
        try {
            response.forward(getResourcePath(path), ContentType.from(path).getMediaType());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private String getResourcePath(String path) throws IOException, URISyntaxException {
        if (path.endsWith("html") || path.endsWith("ico")) {
            return IOUtils.loadFileFromClasspath("./templates" + path);
        }
        return IOUtils.loadFileFromClasspath("./static" + path);
    }
}
