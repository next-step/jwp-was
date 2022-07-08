package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.MediaType;
import webserver.http.RequestMappingControllerAdapter;

public class IndexMappingController extends RequestMappingControllerAdapter {

    @Override
    public boolean checkUrl(String url) {
        return "/".equals(url);
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.redirect("/index.html");
    }
}
