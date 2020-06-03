package http;

public class RequestLine {

    private final String method;
    private final String path;
    private final String queryString;
    private final String protocol;
    private final String version;

    public RequestLine(String method, String path, String queryString, String protocol, String version) {
        this.method = method;
        this.path = path;
        this.queryString = queryString;
        this.protocol = protocol;
        this.version = version;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    public String getQueryString() {
        return queryString;
    }
}
