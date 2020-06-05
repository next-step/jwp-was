package webserver.customhandler;

import http.request.Request;
import http.response.Response;
import webserver.Handler;

import java.io.UnsupportedEncodingException;

public class LoginHandler implements Handler {
    private String url;

    public LoginHandler(String url) {
        this.url = url;
    }

    @Override
    public boolean isSameUrl(String url) {
        return this.url.equals(url);
    }

    @Override
    public Response work(Request request) throws UnsupportedEncodingException {
        return null;
    }

    @Override
    public String getUrl() {
        return this.url;
    }
}
