package webserver.controller;

import utils.IOUtils;
import webserver.ContentType;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticResourceController implements Controller {

    @Override
    public HttpResponse service(HttpRequest request) throws IOException, URISyntaxException {
        String path = request.getPath();

        return HttpResponse.forward(getResourcePath(path), ContentType.from(path).getMediaType());
    }

    private String getResourcePath(String path) throws IOException, URISyntaxException {
        if (path.endsWith("html") || path.endsWith("ico")) {
            return IOUtils.loadFileFromClasspath("./templates" + path);
        }
        return IOUtils.loadFileFromClasspath("./static" + path);
    }
}
