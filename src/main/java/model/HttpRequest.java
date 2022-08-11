package model;

import webserver.RequestLine;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class HttpRequest {

    private HttpHeader header;
    private RequestLine requestLine;
    private RequestBody body;
    private Map<String, Cookie> cookie;

    public HttpRequest(HttpHeader header, String body) throws UnsupportedEncodingException {
        this.header = header;
        this.requestLine = new RequestLine(header.getRequestLine());
        this.cookie = Cookie.createCookie(header.getCookie(), requestLine.getFullRequestPath());
        this.body = new RequestBody(body);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestBody getBody() {
        return body;
    }

    public Cookie getCookie(String name) {
        return cookie.get(name);
    }

    public HttpHeader getHeader() {
        return header;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                ", body=" + body +
                ", cookie=" + cookie +
                '}';
    }
}
