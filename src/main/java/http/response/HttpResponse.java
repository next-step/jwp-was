package http.response;

import java.util.Map;
import java.util.Set;

import http.Cookie;
import http.HttpStatus;
import http.request.Protocol;

public class HttpResponse {

    private final Protocol protocol;
    private final HttpStatus httpStatus;
    private final Map<String, String> headers;
    private final String body;
    private final Set<Cookie> cookies;

    public HttpResponse(Protocol protocol, HttpStatus httpStatus, Map<String, String> headers, String body,
        Set<Cookie> cookies) {
        this.protocol = protocol;
        this.httpStatus = httpStatus;
        this.headers = headers;
        this.body = body;
        this.cookies = cookies;
    }

    public HttpResponse(HttpStatus status, Map<String, String> headers) {
        this(Protocol.of("HTTP/1.1"), status, headers, "", Set.of());
    }

    public HttpResponse(HttpStatus status, Map<String, String> headers, Cookie cookie) {
        this(Protocol.of("HTTP/1.1"), status, headers, "", Set.of(cookie));
    }

    public static HttpResponse found(String location) {
        return new HttpResponse(HttpStatus.FOUND, Map.of("Location", location));
    }

    public static HttpResponse ok(String body) {
        return new HttpResponse(Protocol.of("HTTP/1.1"), HttpStatus.OK, Map.of(), body, Set.of());
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public Set<Cookie> getCookies() {
        return cookies;
    }
}
