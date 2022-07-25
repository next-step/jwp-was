package model;

import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

public class HttpRequest {
    private static final int REQUEST_LINE = 0;
    private static final int REQUEST_HEADER_START = 1;

    private final RequestLine requestLine;
    private final HttpRequestHeader header;

    private HttpRequest(RequestLine requestLine, HttpRequestHeader header) {
        Assert.notNull(requestLine, "requestLine is not null");
        Assert.notNull(header, "header is not null");

        this.requestLine = requestLine;
        this.header = header;
    }

    public static HttpRequest of(List<String> requestLines) {
        return new HttpRequest(
                RequestLineFactory.parsing(requestLines.get(REQUEST_LINE)),
                HttpRequestHeader.of(requestLines.subList(REQUEST_HEADER_START, requestLines.size()))
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
}
