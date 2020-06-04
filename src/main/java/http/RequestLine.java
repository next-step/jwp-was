package http;

public class RequestLine {

    private final String method;
    private final Path path;
    private final Protocol protocol;

    public RequestLine(final String method, final Path path, final Protocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path.getPath();
    }

    public String getProtocol() {
        return protocol.getProtocol();
    }

    public String getVersion() {
        return protocol.getVersion();
    }

    public String getParameter(final String parameter) {
        return path.getParameter(parameter);
    }
}
