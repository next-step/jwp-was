package webserver.customhandler;

import http.request.Request;
import http.response.ContentType;
import http.response.HttpStatus;
import http.response.Response;
import http.response.ResponseBody;
import utils.FileIoUtils;
import webserver.Handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public class StaticResourceHandler implements Handler {
    private static final String PREFIX_STATIC = "./static";

    @Override
    public boolean isSameUrl(String url) {
        return false;
    }

    @Override
    public Response work(Request request) throws IOException, URISyntaxException {
        return new Response(HttpStatus.OK, getContentType(request), getBody(request));
    }

    private ContentType getContentType(Request request){
        return ContentType.findContentType(request.getUrl());
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
