package controller;

import http.MediaType;
import http.HttpRequest;
import http.RequestLine;
import http.HttpResponse;

public class IndexMappingController extends RequestMappingControllerAdapter {

    @Override
    public boolean checkUrl(String url) {
        return "/".equals(url);
    }

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        return new HttpResponse(MediaType.TEXT_HTML_UTF8, requestLine.getPath(), null);
    }
}
