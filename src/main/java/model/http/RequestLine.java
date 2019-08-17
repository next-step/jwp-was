package model.http;

import java.util.Objects;

public class RequestLine {
    private static final String DELEMETER = " ";

    private HttpMethod method;
    private RequestUri requestUri;
    private HttpVersion httpVersion;

    public HttpMethod getMethod() {
        return method;
    }

    public RequestUri getRequestUri() {
        return requestUri;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public UriPath getPath() {
        return requestUri.getUriPath();
    }

    private RequestLine(HttpMethod method, RequestUri requestUri, HttpVersion httpVersion) {
        this.method = method;
        this.requestUri = requestUri;
        this.httpVersion = httpVersion;
    }

    public static RequestLine of(String requestLine) {
        String[] parts = requestLine.split(DELEMETER);
        HttpMethod method = HttpMethod.valueOf(parts[0]);
        RequestUri requestUri = RequestUri.of(parts[1]);
        HttpVersion version = HttpVersion.of(parts[2]);
        return new RequestLine(method, requestUri, version);
    }

    public static RequestLine of(HttpMethod method, RequestUri requestUri, HttpVersion httpVersion) {
        return new RequestLine(method, requestUri, httpVersion);
    }

    public RequestLine appendQuery(Query query) {
        requestUri.appendQuery(query);
        return this;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + method +
                ", requestUri=" + requestUri +
                ", httpVersion=" + httpVersion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return method == that.method &&
                Objects.equals(requestUri, that.requestUri) &&
                httpVersion == that.httpVersion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, requestUri, httpVersion);
    }

    public boolean isSamePath(String path) {
        return requestUri.getUriPath().isSamePath(path);
    }

    public boolean isSameMethod(HttpMethod method) {
        return this.method.equals(method);
    }
}
