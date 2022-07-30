package webserver.request;

import static exception.ExceptionStrings.INVALID_REQUEST;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import webserver.domain.Cookies;
import webserver.enums.HttpMethod;
import webserver.enums.Protocol;
import webserver.domain.HttpHeader;

public class HttpRequest {

    private RequestLine requestLine;
    private HttpRequestHeader header;
    private HttpRequestBody body;

    public HttpRequest(String requestLine) {
        this.requestLine = RequestLine.of(requestLine);
        this.header = HttpRequestHeader.createEmpty();
        this.body = HttpRequestBody.createEmpty();
    }

    public HttpRequest(RequestLine requestLine, HttpRequestHeader httpHeader, HttpRequestBody httpBody) {
        this.requestLine = requestLine;
        this.header = httpHeader;
        this.body = httpBody;
    }

    public static HttpRequest of(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

        String line;
        line = br.readLine();
        if (line == null) {
            throw new IllegalArgumentException(INVALID_REQUEST);
        }

        RequestLine requestLine = RequestLine.of(line);
        HttpRequestHeader httpHeader = new HttpRequestHeader(br);

        HttpRequestBody httpBody = HttpRequestBody.createEmpty();
        if (httpHeader.hasContent()) {
            httpBody = HttpRequestBody.of(br, httpHeader.getHeader(HttpHeader.CONTENT_LENGTH));
        }

        return new HttpRequest(requestLine, httpHeader, httpBody);
    }

    public Map<String, String> bodyQueryString() {
        return body.map();
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpMethod getMethod() {
        return requestLine.getHttpMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public Protocol getProtocol() {
        return requestLine.getProtocol();
    }

    public void addHeader(String key, String value) {
        header.putHeader(key, value);
    }

    public String getHeader(String key) {
        return header.getHeader(key);
    }

    public Cookies getCookies() {
        if (!this.header.contains(HttpHeader.COOKIE)) {
            return Cookies.empty();
        }
        return Cookies.of(this.header.getHeader(HttpHeader.COOKIE));
    }

    public String getQueryString(String queryKey) {
        return bodyQueryString().get(queryKey);
    }
}
