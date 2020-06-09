package http;

import java.util.Objects;

public class RequestLine {
    private final Method method;
    private final String path;
    private final Protocol protocol;
    private final QueryStrings queryStrings;

    public RequestLine(String method, String path, String protocolAndVersion) {
        this.method = Method.valueOf(method);
        this.path = path;
        this.protocol = new Protocol(protocolAndVersion);
        this.queryStrings = QueryStrings.of(path);
    }

    public static RequestLine of(String method, String path, String protocolAndVersion) {
        return new RequestLine(method, path, protocolAndVersion);
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public QueryStrings getQueryStrings() {
        return queryStrings;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestLine that = (RequestLine) o;
        return getMethod() == that.getMethod() &&
                Objects.equals(getPath(), that.getPath()) &&
                Objects.equals(getProtocol(), that.getProtocol());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMethod(), getPath(), getProtocol());
    }

}
