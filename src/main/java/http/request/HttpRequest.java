package http.request;

import http.HttpEntity;
import http.HttpHeaders;
import http.HttpMethod;
import http.request.requestline.Protocol;
import http.request.requestline.RequestLine;

import java.util.Map;

public class HttpRequest {
    private final RequestLine requestLine;
    private final HttpEntity httpEntity;

    public static HttpRequest ofGet(
        RequestLine requestLine,
        Map<String, String> httpHeaders
    ) {
        return new HttpRequest(requestLine, new HttpEntity(new HttpHeaders(httpHeaders)));
    }

    public static HttpRequest ofPost(
        RequestLine requestLine,
        Map<String, String> httpHeaders,
        String httpBody
    ) {
       return new HttpRequest(requestLine, new HttpEntity(new HttpHeaders(httpHeaders), httpBody));
    }

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

    public Protocol getProtocol() {
        return requestLine.getProtocol();
    }

    public String getPath() {
        return requestLine.getPath();
    }
}
