package model;

public class HttpRequestHeader {
    private final RequestLine requestLine;
    private final HttpHeader httpHeader;

    public HttpRequestHeader(RequestLine requestLine, HttpHeader httpHeader) {
        this.requestLine = requestLine;
        this.httpHeader = httpHeader;
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
}
