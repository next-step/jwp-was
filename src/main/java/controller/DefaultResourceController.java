package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.view.ResourceViewResolver;
import webserver.http.view.View;
import webserver.http.view.ViewResolver;

public class DefaultResourceController extends AbstractController {

    private static final String ROOT_PATH = "/";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws Exception {
        ViewResolver viewResolver = new ResourceViewResolver();
        View view = viewResolver.resolveView(getDefaultPath(request.getPath()));

        view.render(response);
    }

    private String getDefaultPath(String path) {
        if (ROOT_PATH.equals(path)) {
            return "/index.html";
        }
        return path;
    }
}
