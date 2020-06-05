package http.request;

import http.HttpMethod;

import java.util.Optional;

public class HttpRequest {
    private final RequestLine requestLine;

    public HttpRequest(RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }
}
