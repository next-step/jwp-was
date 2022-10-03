package webserver.http.request;

import webserver.http.HttpHeaders;

public class HttpRequest {

    private final RequestLine requestLine;
    private final HttpHeaders httpHeaders;
    private final RequestBody requestBody;

    public HttpRequest(RequestLine requestLine, HttpHeaders httpHeaders, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.requestBody = requestBody;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public RequestBody getBody() {
        return requestBody;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestLine=" + requestLine +
                '}';
    }
}
