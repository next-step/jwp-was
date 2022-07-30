package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class DefaultResourceController extends AbstractController {

    private static final String ROOT_PATH = "/";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        String path = getDefaultPath(request.getPath());

        response.forward(path);
    }

    private String getDefaultPath(String path) {
        if (ROOT_PATH.equals(path)) {
            return "/index.html";
        }
        return path;
    }
}
