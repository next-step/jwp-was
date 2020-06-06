package webserver;

import http.request.Request;
import http.response.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public interface Handler {
    boolean isSameUrl(String url);
    Response work(Request request) throws UnsupportedEncodingException, URISyntaxException, IOException;
    String getUrl();
}
