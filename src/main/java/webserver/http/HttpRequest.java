package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

public class HttpRequest {
    public static final String VALIDATION_MESSAGE = "잘못된 요청입니다.";
    public static final String SESSION_ID_KEY = "sessionId";
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class);

    private final RequestLine requestLine;
    private final Headers headers;
    private final Attributes attributes;

    public HttpRequest(RequestLine requestLine, Headers headers, Attributes attributes) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(requestLine, that.requestLine) && Objects.equals(headers, that.headers) && Objects.equals(attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestLine, headers, attributes);
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getHttpMethod();
    }

    public boolean isGet() {
        return requestLine.isGet();
    }

    public boolean isPost() {
        return requestLine.isPost();
    }

    public String getPath() {
        return requestLine.getPath();    }

    public HttpProtocol getHttpProtocol() {
        return requestLine.getHttpProtocol();
    }

    public <T> T getHeader(String key, Class<T> returnType) {
        return headers.getHeader(key, returnType);
    }
    public String getHeader(String key) {
        return headers.getHeader(key);
    }

    public int getContentLength() {
        try {
            return headers.getHeader("Content-Length", Integer.class);
        } catch (Exception e) {
            return 0;
        }
    }

    public Map<String, Object> getAttributes() {
        return attributes.getAttributes();
    }

    public String getAttribute(String key) {
        return attributes.getAttribute(key);
    }

    public <T> T getCookie(String key, Class<T> returnType) {
        return headers.getCookie(key, returnType);
    }

    public String getCookie(String key) {
        return headers.getCookie(key);
    }

    public String getSessionCookie() {
        return getCookie(SESSION_ID_KEY);
    }
}
