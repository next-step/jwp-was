package model;

import utils.IOUtils;
import webserver.RequestLine;

import java.io.*;
import java.util.Map;

public class HttpRequest {

    private HttpHeader header;
    private RequestLine requestLine;
    private RequestBody body;
    private Map<String, Cookie> cookie;

    public HttpRequest(HttpHeader header, RequestLine line, RequestBody body) throws IOException {

        this.requestLine = line;
        this.header = header;
        this.cookie = Cookie.createCookie(header);
        this.body = body;
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
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

    public String getHeader(String name) {
        return header.getValue(name);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                ", body=" + body +
                ", cookie=" + cookie +
                '}';
    }

    public String getPath() {
        return requestLine.getRequestPath();
    }

    public String getParameter(String param) {
        if (requestLine.getMethod() == HttpMethod.GET) {
            return requestLine.getRequestParams(param);
        }
        return body.getFirstValue(param);
    }
}
