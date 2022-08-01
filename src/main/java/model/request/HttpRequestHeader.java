package model.request;


import model.HttpHeader;

import java.util.Map;

public class HttpRequestHeader {
    private final RequestLine requestLine;
    private final HttpHeader httpHeader;
    private final Map<String, String> requestBody;

    private HttpRequestHeader(RequestLine requestLine, HttpHeader httpHeader, Map<String, String> requestBody) {
        this.requestLine = requestLine;
        this.httpHeader = httpHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequestHeader getRequestHeaderOf(RequestLine requestLine, HttpHeader httpHeader) {
        return new HttpRequestHeader(requestLine, httpHeader, null);
    }

    public static HttpRequestHeader postRequestHeaderWithBody(RequestLine requestLine, HttpHeader httpHeader, Map<String, String> requestBody) {
        return new HttpRequestHeader(requestLine, httpHeader, requestBody);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public Boolean isEqualPath(String path) {
        return requestLine.getPath().equals(path);
    }

    public Map<String, String> getQueryString() {
        return requestLine.getQueryString();
    }

    public Map<String, String> getRequestBody() {
        return requestBody;
    }
}
