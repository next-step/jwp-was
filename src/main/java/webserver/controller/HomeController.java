package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.request.requestline.Path;
import webserver.http.response.HttpResponse;

public class HomeController extends MethodController{
    @Override
    HttpResponse processGet(HttpRequest httpRequest) {
        return HttpResponse.redirect("/index.html");
    }

    @Override
    HttpResponse processPost(HttpRequest httpRequest) {
        return HttpResponse.notFound();
    }

    @Override
    public boolean isMatchPath(HttpRequest httpRequest) {
        return httpRequest.isPathEqual(new Path("/"));
    }
}