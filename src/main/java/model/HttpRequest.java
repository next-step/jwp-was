package model;

import webserver.RequestLine;
import webserver.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class HttpRequest {

    private RequestLine requestLine;
    private Body body;

    public HttpRequest(RequestLine requestLine, String body) throws UnsupportedEncodingException {
        this.requestLine = requestLine;
        this.body = new Body(URLDecoder.decode(body, "UTF-8"));
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public Body getBody() {
        return body;
    }
}
