package http;

public class RequestHeaderFirstLine {
    private final String method;
    private final String path;
    private final String protocol;

    public RequestHeaderFirstLine(String method, String path, String protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public String getProtocol() {
        return protocol;
    }
}
