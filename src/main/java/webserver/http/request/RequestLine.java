package webserver.http.request;

import java.util.Map;
import java.util.Objects;

public class RequestLine {
    public static final String VALUE_SEPARATOR = " ";

    private final HttpMethod httpMethod;
    private final Path path;
    private final Protocol protocol;

    public RequestLine(RequestLine requestLine) {
        this.httpMethod = requestLine.httpMethod;
        this.path = requestLine.path;
        this.protocol = requestLine.protocol;
    }

    public RequestLine(HttpMethod httpMethod, Path path, Protocol protocol) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.protocol = protocol;
    }

    public RequestLine(String requestValue) {
        this(makeRequestLine(requestValue));
    }

    private static RequestLine makeRequestLine(String requestValue) {
        String[] values = requestValue.split(VALUE_SEPARATOR);
        return new RequestLine(HttpMethod.valueOf(values[0]), new Path(values[1]), new Protocol(values[2]));
    }

    public RequestLine(String method, String path, String protocol) {
        this(HttpMethod.valueOf(method), new Path(path), new Protocol(protocol));
    }

    public String getPathWithoutQueryString() {
        return path.getPath();
    }

    public Map<String, String> getQueryStringWithoutPathFromPath() {
        return path.getQueryString().getQueryString();
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Path getPath() {
        return path;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return httpMethod == that.httpMethod && Objects.equals(path, that.path) && Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, path, protocol);
    }
}
