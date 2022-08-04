package model.request;


import model.HttpHeader;

import java.util.Map;

public class HttpRequestMessage {
    private final RequestLine requestLine;
    private final HttpHeader httpHeader;
    private final Map<String, String> requestBody;

    private HttpRequestMessage(RequestLine requestLine, HttpHeader httpHeader, Map<String, String> requestBody) {
        this.requestLine = requestLine;
        this.httpHeader = httpHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequestMessage getRequestHeaderOf(RequestLine requestLine, HttpHeader httpHeader) {
        return new HttpRequestMessage(requestLine, httpHeader, null);
    }

    public static HttpRequestMessage postRequestHeaderWithBody(RequestLine requestLine, HttpHeader httpHeader, Map<String, String> requestBody) {
        return new HttpRequestMessage(requestLine, httpHeader, requestBody);
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

    public Map<String, String> getRequestBody() {
        return requestBody;
    }

    public boolean hasCookie(String cookie) {
        return httpHeader.hasCookie(cookie);
    }
}
