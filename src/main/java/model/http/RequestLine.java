package model.http;

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
}
