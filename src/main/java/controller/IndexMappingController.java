package controller;

import http.HttpRequest;
import http.HttpResponse;
import http.MediaType;

public class IndexMappingController extends RequestMappingControllerAdapter {

    @Override
    public boolean checkUrl(String url) {
        return "/".equals(url);
    }

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        return new HttpResponse(MediaType.TEXT_HTML_UTF8, httpRequest.getPath(), null);
    }
}
