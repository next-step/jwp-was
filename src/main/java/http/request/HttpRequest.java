package http.request;

import http.HttpEntity;
import http.HttpMethod;

import java.util.Optional;

public class HttpRequest {
    private final RequestLine requestLine;
    private final HttpEntity httpEntity;

    public HttpRequest(RequestLine requestLine) {
        this(requestLine, null);
    }

    public HttpRequest(RequestLine requestLine, HttpEntity httpEntity) {
        this.requestLine = requestLine;
        this.httpEntity = httpEntity;
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }
}
