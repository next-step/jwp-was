package webserver.handler.custom;

import http.request.Request;
import http.request.headers.Headers;
import http.response.headers.ContentType;
import http.response.responseline.HttpStatus;
import http.response.Response;
import http.response.body.ResponseBody;
import utils.FileIoUtils;
import webserver.handler.Handler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class StaticResourceHandler implements Handler {
    private static final String PREFIX_STATIC = "./static";

    @Override
    public boolean isSameUrl(String url) {
        return false;
    }

    @Override
    public Response work(Request request) throws IOException, URISyntaxException {
        ResponseBody body = getBody(request);
        return new Response(HttpStatus.OK, getContentType(request), getHeaders(body, getContentType(request)), body);
    }

    private ContentType getContentType(Request request) {
        return ContentType.findContentType(request.getUrl());
    }

    private Headers getHeaders(ResponseBody body, ContentType contentType) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type: ", contentType.getContentType());
        headers.put("Content-Length: ", String.valueOf(body.getBody().length));

        return new Headers(headers);
    }

    private ResponseBody getBody(Request request) throws IOException, URISyntaxException {
        byte[] bytes = FileIoUtils.loadFileFromClasspath(PREFIX_STATIC + request.getUrl());
        return new ResponseBody(bytes);
    }

    @Override
    public String getUrl() {
        return null;
    }
}
