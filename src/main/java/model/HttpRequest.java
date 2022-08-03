package model;

import webserver.RequestLine;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class HttpRequest {

    private RequestLine requestLine;
    private RequestBody body;

    public HttpRequest(RequestLine requestLine, String body) throws UnsupportedEncodingException {
        this.requestLine = requestLine;
        this.body = new RequestBody(body);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestBody getBody() {
        return body;
    }
}
