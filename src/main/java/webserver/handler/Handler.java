package webserver.handler;

import http.request.Request;
import http.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Handler {
    boolean isSameUrl(String url);

    Response work(Request request) throws URISyntaxException, IOException;

    String getUrl();
}
