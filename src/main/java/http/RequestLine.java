package http;

public class RequestLine {

    private final String method;
    private final String path;
    private final String queryString;
    private final Protocol protocol;

    public RequestLine(String method, String path, String queryString, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.queryString = queryString;
        this.protocol = protocol;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol.getProtocol();
    }

    public String getVersion() {
        return protocol.getVersion();
    }

    public String getQueryString() {
        return queryString;
    }
}
