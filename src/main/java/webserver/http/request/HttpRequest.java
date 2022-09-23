package webserver.http.request;

import webserver.http.HttpBody;
import webserver.http.HttpHeaders;

public class HttpRequest {

    private final RequestLine requestLine;
    private final HttpHeaders httpHeaders;
    private final HttpBody httpBody;

    public HttpRequest(RequestLine requestLine, HttpHeaders httpHeaders, HttpBody httpBody) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.httpBody = httpBody;
    }

    public boolean isStaticResource() {
        return requestLine.isStaticResource();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public HttpBody getHttpBody() {
        return httpBody;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                '}';
    }
}
