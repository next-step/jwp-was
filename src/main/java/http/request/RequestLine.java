package http.request;

import http.HttpMethod;
import http.Protocol;

import java.util.Objects;

public class RequestLine {
    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final String URL_DELIMITER = "\\?";

    private final HttpMethod method;
    private final String path;
    private final String queryString;
    private final Protocol protocol;

    private RequestLine(final HttpMethod method,
                       final String path,
                       final String queryString,
                       final Protocol protocol) {
        this.method = method;
        this.path = path;
        this.queryString = queryString;
        this.protocol = protocol;
    }

    public static RequestLine of(final HttpMethod method,
                                 final String path,
                                 final String queryString,
                                 final Protocol protocol) {
        return new RequestLine(method, path, queryString, protocol);
    }

    public static RequestLine of(final String request) {
        final String[] values = request.split(REQUEST_LINE_DELIMITER);
        final String[] urls = values[1].split(URL_DELIMITER);
        return new RequestLine(
                HttpMethod.getHttpMethod(values[0]),
                urls[0],
                urls.length == 2 ? urls[1] : null,
                Protocol.of(values[2])
        );
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public String getQueryString() {
        return queryString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return method == that.method &&
                Objects.equals(path, that.path) &&
                Objects.equals(queryString, that.queryString) &&
                Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, queryString, protocol);
    }
}
