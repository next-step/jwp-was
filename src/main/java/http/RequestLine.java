package http;

public class RequestLine {

    private final Method method;
    private final RequestPath requestPath;
    private final String protocol;
    private final QueryStrings queryStrings;

    protected RequestLine(Method method, String path, String protocol) {
        this.method = method;
        this.requestPath = RequestPath.of(path);
        this.protocol = protocol;
        this.queryStrings = new QueryStrings(path);
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

    public String getProtocol() {
        return protocol;
    }

    public String getQueryValue(final String key) {
        return queryStrings.getValue(key);
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
