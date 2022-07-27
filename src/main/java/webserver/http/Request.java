package webserver.http;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Request {

    private final RequestLine requestLine;

    private final Headers headers;

    private final String body;

    private final List<Cookie> cookies;

    public Request(RequestLine requestLine, Headers headers, String body) {
        this.requestLine = requireNonNull(requestLine, "");
        this.headers = requireNonNull(headers, "");
        this.body = body;
        this.cookies = Cookie.listOf(this.headers.getValue("cookie"));
    }

    public Request(RequestLine requestLine, Headers headers) {
        this(requestLine, headers, "");
    }

    public Request(RequestLine requestLine) {
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

    public String getRequestBody() {
        return body;
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

    @Override
    public String toString() {
        return requestLine.toString() + " " + headers.toString() + " " + body;
    }
}
