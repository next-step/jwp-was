package webserver.controller;

import http.httprequest.HttpRequest;
import http.httprequest.requestline.HttpMethod;
import http.httpresponse.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {
    String getPath();
    boolean isMatch(HttpMethod httpMethod, String path);
    HttpResponse execute(HttpRequest httpRequest) throws IOException, URISyntaxException;
}
