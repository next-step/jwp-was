package webserver.http;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class HttpRequest {

    private final RequestLine requestLine;

    private final Headers headers;

    private final RequestBody body;

    private final List<Cookie> cookies;

    public HttpRequest(RequestLine requestLine, Headers headers, String body) {
        this.requestLine = requireNonNull(requestLine, "");
        this.headers = requireNonNull(headers, "");
        this.body = new RequestBody(body);
        this.cookies = Cookie.listOf(this.headers.getValue("cookie"));
    }

    public HttpRequest(RequestLine requestLine, Headers headers) {
        this(requestLine, headers, "");
    }

    public HttpRequest(RequestLine requestLine) {
        this(requestLine, new Headers());
    }

    public String getPath() {
        return requestLine.getPath().getPath();
    }

    public Headers getHeaders() {
        return headers;
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getBodyValue(String name) {
        return body.getValue(name);
    }

    public String getCookie(String name) {
        return cookies.stream()
                .filter(cookie -> cookie.equalName(name))
                .findAny()
                .map(Cookie::getValue)
                .orElse("");
    }

    RequestLine getRequestLine() {
        return requestLine;
    }

    RequestBody getBody() {
        return body;
    }

    @Override
    public String toString() {
        return requestLine.toString() + " " + headers.toString() + " " + body;
    }
}
