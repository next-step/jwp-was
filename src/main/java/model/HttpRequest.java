package model;

import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

public class HttpRequest {
    private static final int REQUEST_LINE = 0;
    private static final int REQUEST_HEADER_START = 1;

    private final RequestLine requestLine;
    private final HttpRequestHeader header;
    private final HttpRequestBody body;

    private HttpRequest(RequestLine requestLine, HttpRequestHeader header, HttpRequestBody body) {
        Assert.notNull(requestLine, "requestLine is not null");
        Assert.notNull(header, "header is not null");

        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
    }

    public static HttpRequest of(List<String> requestLines) {
        return new HttpRequest(
                RequestLineFactory.parsing(requestLines.get(REQUEST_LINE)),
                HttpRequestHeader.of(requestLines.subList(REQUEST_HEADER_START, requestLines.size())),
                HttpRequestBody.empty()
        );
    }

    public Map<String, String> getHeader() {
        return header.getHeaders();
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public boolean hasContent() {
        return header.getContentLength() > 0;
    }

    public int getContentLength() {
        return header.getContentLength();
    }

    public HttpRequest writeBody(HttpRequestBody body) {
        return of(this, body);
    }

    private HttpRequest of(HttpRequest httpRequest, HttpRequestBody body) {
        return new HttpRequest(httpRequest.requestLine, httpRequest.header, body);
    }
}
