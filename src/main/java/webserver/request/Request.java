package webserver.request;

import webserver.common.Protocol;

public class Request {
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public Request(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public RequestMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getQuery(String key) {
        return requestLine.getQuery(key);
    }


    public Protocol getProtocol() {
        return requestLine.getProtocol();
    }


    public String getContentType() {
        return requestHeader.getContentType();
    }

    public int getContentLength() {
        return requestHeader.getContentLength();
    }

    public String getCookie() {
        return requestHeader.getCookie();
    }

    public String getBody(String key) {
        return requestBody.get(key);
    }
}
