package webserver.customhandler;

import http.request.Request;
import http.response.Response;
import webserver.Handler;

import java.io.UnsupportedEncodingException;

public class TemplateHandler implements Handler {
    @Override
    public boolean isSameUrl(String url) {
        return false;
    }

    @Override
    public Response work(Request request) throws UnsupportedEncodingException {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }
}
