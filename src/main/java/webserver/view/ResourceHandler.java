package webserver.view;

import webserver.AbstractRequestMappingHandler;
import webserver.ResourceLoader;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

import static webserver.http.HttpHeaders.CONTENT_LENGTH;
import static webserver.http.HttpHeaders.CONTENT_TYPE;

public class ResourceHandler extends AbstractRequestMappingHandler {

    @Override
    public void process(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        String path = request.getRequestUriPath();

        String resourcePath = ResourceLoader.getResourcePath(path);
        byte [] body = ResourceLoader.loadResource(resourcePath);

        response.addHeader(CONTENT_LENGTH, body.length);
        response.addHeader(CONTENT_TYPE, ResourceLoader.resourceContentType(path));

        response.response200Header();
        response.responseBody(body);
    }
}
