package webserver.view;

import webserver.AbstractHandler;
import webserver.ResourceLoader;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.template.ViewResolver;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceHandler extends AbstractHandler {

    public ResourceHandler(ViewResolver viewResolver) {
        super(viewResolver);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        String path = request.getRequestUriPath();

        byte[] body = viewResolver.loadView(path);

        response.response200Header(body.length, ResourceLoader.resourceContentType(path));
        response.responseBody(body);
    }
}
