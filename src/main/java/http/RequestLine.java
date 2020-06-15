package http;

import java.util.Objects;

public class RequestLine {
    private final Method method;
    private final RequestPath requestPath;
    private final Protocol protocol;
    private final QueryStrings queryStrings;

    public RequestLine(String method, String path, String protocolAndVersion) {
        this.method = Method.valueOf(method);
        this.requestPath = RequestPath.of(path);
        this.protocol = Protocol.of(protocolAndVersion);
        this.queryStrings = QueryStrings.of(path);
    }

    public static RequestLine of(String method, String path, String protocolAndVersion) {
        return new RequestLine(method, path, protocolAndVersion);
    }

    public Method getMethod() {
        return method;
    }

    public RequestPath getRequestPath() {
        return requestPath;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public QueryStrings getQueryStrings() {
        return queryStrings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestLine)) return false;
        RequestLine that = (RequestLine) o;
        return getMethod() == that.getMethod() &&
                Objects.equals(getRequestPath(), that.getRequestPath()) &&
                Objects.equals(getProtocol(), that.getProtocol()) &&
                Objects.equals(getQueryStrings(), that.getQueryStrings());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMethod(), getRequestPath(), getProtocol(), getQueryStrings());
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + method +
                ", requestPath=" + requestPath +
                ", protocol=" + protocol +
                ", queryStrings=" + queryStrings +
                '}';
    }
}
