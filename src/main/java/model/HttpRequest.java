package model;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HttpRequest {
    private static final int REQUEST_LINE = 0;
    private static final int REQUEST_HEADER_START = 1;

    private final RequestLine requestLine;
    private final HttpRequestHeader header;
    private final HttpRequestBody body;

    private HttpRequest(RequestLine requestLine, HttpRequestHeader header, HttpRequestBody body) {
        Objects.requireNonNull(requestLine, "requestLine is not null");
        Objects.requireNonNull(header, "header is not null");
        Objects.requireNonNull(body, "body is not null");

        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
    }

    public static HttpRequest of(BufferedReader bufferedReader) throws IOException {
        List<String> requestLines = IOUtils.readLines(bufferedReader);
        HttpRequest request = new HttpRequest(
                RequestLineFactory.parsing(requestLines.get(REQUEST_LINE)),
                HttpRequestHeader.of(requestLines.subList(REQUEST_HEADER_START, requestLines.size())),
                HttpRequestBody.empty()
        );
        if (request.hasContent()) {
            return request.writeBody(HttpRequestBody.of(IOUtils.readData(bufferedReader, request.getContentLength())));
        }
        return request;
    }

    public static HttpRequest of(RequestLine requestLine, HttpRequestHeader header, HttpRequestBody body) {
        return new HttpRequest(requestLine, header, body);
    }

    public Map<String, String> getHeader() {
        return header.getHeaders();
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public boolean hasContent() {
        return header.getContentLength() > 0;
    }

    public int getContentLength() {
        return header.getContentLength();
    }

    public HttpRequest writeBody(HttpRequestBody body) {
        return of(this, body);
    }

    private HttpRequest of(HttpRequest httpRequest, HttpRequestBody body) {
        return new HttpRequest(httpRequest.requestLine, httpRequest.header, body);
    }

    public boolean isMatch(HttpMethod method, Path path) {
        return requestLine.isMatchHttpMethod(method, path);
    }

    public HttpRequestBody getBody() {
        return body;
    }

    public String getParameter(String value) {
        return body.getValue(value);
    }

    public boolean isMatchMethod(HttpMethod httpMethod) {
        return getHttpMethod().equals(httpMethod);
    }

    public boolean isLogin() {
        return header.containsLoginCookie();
    }
}
