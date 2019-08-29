package model.http;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class HttpRequestHeader {
    private RequestLine requestLine;
    private ContentLength contentLength;

    private HttpRequestHeader(RequestLine requestLine) {
        if (!validateRequestLine(requestLine)) throw new IllegalArgumentException("wrong request line");
        this.requestLine = requestLine;
    }

    private HttpRequestHeader(RequestLine requestLine, ContentLength contentLength) {
        if (!validateRequestLine(requestLine)) throw new IllegalArgumentException("wrong request line");
        this.requestLine = requestLine;
        this.contentLength = contentLength;
    }

    public static HttpRequestHeader of(RequestLine requestLine) {
        return new HttpRequestHeader(requestLine);
    }

    public static HttpRequestHeader of(RequestLine requestLine, int contentLength) {
        return new HttpRequestHeader(requestLine, ContentLength.of(contentLength));
    }

    public static HttpRequestHeader of(List<String> headerLines) {
        int requestHeaderLineIndex = 0;
        RequestLine requestLine = null;
        ContentLength contentLength = null;

        for (String headerLine : headerLines) {
            if (++requestHeaderLineIndex == 1) {
                requestLine = RequestLine.of(headerLine);
            }

            if (headerLine.startsWith(ContentLength.HEADER_PREFIX)) {
                contentLength = ContentLength.of(headerLine.replace(ContentLength.HEADER_PREFIX, ""));
            }
        }

        return new HttpRequestHeader(requestLine, contentLength);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public ContentLength getContentLength() {
        return contentLength;
    }

    private boolean validateRequestLine(RequestLine requestLine) {
        return requestLine != null;
    }

    public boolean containsBody() {
        return requestLine.getMethod().containsBody();
    }

    public Optional<QueryParameter> findQueryParameterByName(String name) {
        return requestLine.getRequestUri().getQuery().findByName(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequestHeader that = (HttpRequestHeader) o;
        return Objects.equals(requestLine, that.requestLine) &&
                Objects.equals(contentLength, that.contentLength);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine, contentLength);
    }

    @Override
    public String toString() {
        return "HttpRequestHeader{" +
                "requestLine=" + requestLine +
                ", contentLength=" + contentLength +
                '}';
    }
}
