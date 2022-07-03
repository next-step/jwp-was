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
    public HttpResponse doGet(HttpRequest httpRequest) {
        return new HttpResponse(MediaType.TEXT_HTML_UTF8, "/index.html", null);
    }
}
