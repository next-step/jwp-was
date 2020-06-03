package http;

import java.util.Objects;

public class RequestLine {

    private final String method;
    private final String path;
    private final Protocol protocol;

    public static RequestLine ofGet(final String path, final String protocol) {
        return new RequestLine("GET", path, new Protocol(protocol));
    }

    RequestLine(final String method, final String path, final Protocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) &&
                Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path);
    }
}
