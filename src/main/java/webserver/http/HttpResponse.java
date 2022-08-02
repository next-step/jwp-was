package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class HttpResponse {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpResponse.class);

    private final StatusLine statusLine;
    private final Headers headers = new Headers();
    private final String path;
    private final Object content;

    private HttpResponse(HttpStatus httpStatus, String path) {
        this.statusLine = new StatusLine(httpStatus);
        this.path = path;
        this.content = null;
    }

    private HttpResponse(HttpStatus httpStatus, String path, Object content) {
        this.statusLine = new StatusLine(httpStatus);
        this.path = path;
        this.content = content;
    }

    public static HttpResponse forward(String path) {
        return new HttpResponse(HttpStatus.OK, path);
    }

    public static HttpResponse forward(String path, Object context) {
        return new HttpResponse(HttpStatus.OK, path, context);
    }

    public static HttpResponse sendRedirect(String path) {
        final HttpResponse httpResponse = new HttpResponse(HttpStatus.FOUND, path);
        httpResponse.addHeader("Location", path);
        return httpResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpResponse that = (HttpResponse) o;
        return Objects.equals(statusLine, that.statusLine) && Objects.equals(headers, that.headers) && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusLine, headers, path);
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public Headers getHeaders() {
        return headers;
    }

    public String getPath() {
        return path;
    }

    public Object getContent() {
        return content;
    }

    public void addHeader(String key, Object value) {
        headers.setHeader(key, value);
    }

    public void setContentLength(int length) {
        addHeader("Content-Length", length);
    }

    public boolean isDynamic() {
        return !path.contains(".");
    }

    public void setCookie(String key, Object value) {
        headers.setHeader("Set-Cookie", String.format("%s=%s; Path=/", key, value));
        headers.setCookie(key, value);
    }
}
