package webserver.handler.custom;

import http.request.Headers;
import http.request.Request;
import http.response.ContentType;
import http.response.HttpStatus;
import http.response.Response;
import http.response.ResponseBody;
import utils.FileIoUtils;
import webserver.handler.Handler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class StaticResourceHandler implements Handler {
    private static final String PREFIX_STATIC = "./static";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";

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
        headers.put(CONTENT_TYPE, contentType.getContentType());
        headers.put(CONTENT_LENGTH, String.valueOf(body.getBody().length));
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
