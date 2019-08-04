package webserver.view;

import webserver.RequestMappingHandler;
import webserver.ResourceLoader;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceHandler implements RequestMappingHandler {

    @Override
    public void handleRequest(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        String path = request.getRequestUriPath();

        String resourcePath = ResourceLoader.getResourcePath(path);
        byte [] body = ResourceLoader.loadResource(resourcePath);

        response.response200Header(body.length, ResourceLoader.resourceContentType(path));
        response.responseBody(body);
    }
}
