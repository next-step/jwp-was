package http;

public class RequestHeaderFirstLine {
    private final HttpMethod method;
    private final Path path;
    private final String protocol;

    public RequestHeaderFirstLine(HttpMethod method, String path, String protocol) {
        this.method = method;
        this.path = Path.of(path);
        this.protocol = protocol;
    }

    public Path getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getProtocol() {
        return protocol;
    }
}
