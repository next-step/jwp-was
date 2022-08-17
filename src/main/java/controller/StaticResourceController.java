package controller;

import view.StaticResourceView;
import view.View;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.request.Path;

public class StaticResourceController extends AbstractController {
    @Override
    void doPost(HttpRequest request, HttpResponse response) throws Exception {
    }

    @Override
    void doGet(HttpRequest request, HttpResponse response) throws Exception {
        View view = new StaticResourceView();
        view.render(request, response, getLocation(request.getPath()));
    }

    private String getLocation(String path) {
        return !Path.PATH_DELIMITER.equals(path) ? path : "/index.html";
    }
}
