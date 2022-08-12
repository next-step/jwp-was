package controller;

import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticController implements Controller {

    private static final String STATIC_PATH = "./static";

    @Override
    public boolean match(HttpRequest request) {
        return request.isMatchMethod(HttpMethod.GET) && existsFile(request);
    }

    @Override
    public HttpResponse execute(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        return response.forward(addStaticPath(request.getPath()), request.getPath());
    }

    private boolean existsFile(HttpRequest request) {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResource(addStaticPath(request.getPath())) != null;
    }

    private String addStaticPath(String path) {
        return STATIC_PATH + path;
    }
}
