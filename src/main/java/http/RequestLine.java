package http;

import java.util.Objects;

public class RequestLine {

    private final Method method;
    private final RequestPath requestPath;
    private final String protocol;

    protected RequestLine(Method method, String path, String protocol) {
        this.method = method;
        this.requestPath = RequestPath.of(path);
        this.protocol = protocol;
    }

    public static RequestLine of(final String method, final String path, final String protocol) {
        if (Method.GET.toString().equals(method)) {
            return new RequestLine(Method.GET, path, protocol);
        }
        return new RequestLine(Method.POST, path, protocol);
    }

    public String getHandlerPath() {
        return method.toString() + " " + requestPath.getPath();
    }

    public String getRequestPath() {
        return requestPath.getPath();
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return requestPath.getPath();
    }

    public String getProtocol() {
        return protocol;
    }

    public String getQuery() {
        return requestPath.getQuery();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestLine that = (RequestLine) o;
        return getMethod() == that.getMethod() &&
                Objects.equals(getRequestPath(), that.getRequestPath()) &&
                Objects.equals(getProtocol(), that.getProtocol());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMethod(), getRequestPath(), getProtocol());
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + method +
                ", requestPath=" + requestPath +
                ", protocol='" + protocol + '\'' +
                '}';
    }
}
