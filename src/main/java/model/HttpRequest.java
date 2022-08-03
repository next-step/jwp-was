package model;

import webserver.RequestLine;

import java.io.UnsupportedEncodingException;

public class HttpRequest {

    private RequestLine requestLine;
    private RequestBody body;
    private Cookie cookie;

    public HttpRequest(HttpHeader header, String body) throws UnsupportedEncodingException {
        this.requestLine = new RequestLine(header.getRequestLine());
        this.cookie = new Cookie(header.getCookie(), requestLine.getRequestPath());
        this.body = new RequestBody(body);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestBody getBody() {
        return body;
    }

    public Cookie getCookie() {
        return cookie;
    }
}
