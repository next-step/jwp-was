package http.response;

import java.util.Map;

import http.HttpStatus;
import http.request.Protocol;

public class HttpResponse {

    private final Protocol protocol;
    private final HttpStatus httpStatus;
    private final Map<String, String> headers;
    private final String body;

    public HttpResponse(Protocol protocol, HttpStatus httpStatus, Map<String, String> headers, String body) {
        this.protocol = protocol;
        this.httpStatus = httpStatus;
        this.headers = headers;
        this.body = body;
    }

    public HttpResponse(HttpStatus httpStatus, Map<String, String> headers, String body) {
        this(Protocol.of("HTTP/1.1"), httpStatus, headers, body);
    }

    public HttpResponse(HttpStatus status) {
        this(Protocol.of("HTTP/1.1"), status, Map.of(), "");
    }

    public HttpResponse(HttpStatus status, Map<String, String> headers) {
        this(Protocol.of("HTTP/1.1"), status, headers, "");
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
}
