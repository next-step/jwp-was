package http;

public class RequestHeaderLine {
    private final HttpMethod method;
    private final Path path;
    private final String protocol;

    public RequestHeaderLine(HttpMethod method, String path, String protocol) {
        this.method = method;
        this.path = Path.of(path);
        this.protocol = protocol;
    }

    public static RequestHeaderLine of(String value) {
        String[] values = value.split(" ");
        return new RequestHeaderLine(HttpMethod.of(values[0]), values[1], values[2]);
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
