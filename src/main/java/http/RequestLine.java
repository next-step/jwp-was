package http;

public class RequestLine {
    private final HttpMethod httpMethod;
    private final Path path;
    private final Protocol protocol;

    public RequestLine(final HttpMethod httpMethod, final Path path, final Protocol protocol) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.protocol = protocol;
    }

    public String getMethod() {
        return httpMethod.name();
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
