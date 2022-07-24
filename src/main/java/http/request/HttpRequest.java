package http.request;

import http.Headers;

public class HttpRequest {

    private final RequestLine requestLine;
    private final Headers headers;
    private final String body;

    public HttpRequest(RequestLine requestLine, Headers headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public boolean isStaticFile() {
        return requestLine.isStaticFile();
    }

    public String getUrl() {
        return requestLine.getUrl();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getBody() {
        return body;
    }
}
