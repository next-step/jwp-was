package webserver;

import http.request.Request;
import http.response.Response;

import java.io.UnsupportedEncodingException;

public interface Handler {
    boolean isSameUrl(String url);
    Response work(Request request) throws UnsupportedEncodingException;
    String getUrl();
}
