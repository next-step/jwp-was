package http;

public abstract class RequestLine {

    private final Method method;
    private final RequestPath requestPath;
    private final Protocol protocol;
    private final QueryStrings queryStrings;

    protected RequestLine(Method method, String path, String protocolAndVersion) {
        this.method = method;
        this.requestPath = RequestPath.of(path);
        this.protocol = Protocol.of(protocolAndVersion);
        this.queryStrings = QueryStrings.ofGet(path);
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
}
