package http;

public class RequestLine {
    private final String method;
    private final String path;
    private final Protocol protocol;

    public RequestLine(String method, String path, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Protocol getProtocol() {
        return protocol;
    }
}
