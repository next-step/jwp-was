package http;

public class RequestHeaderLine {
    private final HttpMethod method;
    private final Path path;
    private final String protocol;
    public final static String CONTENT_LENGTH_KEY = "Content-Length:";
    public final static String CONTENT_TYPE_KEY = "Content-Type:";
    public final static String COOKIE_KEY = "Cookie:";

    public RequestHeaderLine(HttpMethod method, String path, String protocol) {
        this.method = method;
        this.path = Path.of(path);
        this.protocol = protocol;
    }

    public static RequestHeaderLine of(String value) {
        if (value == null)
            return null;
        String[] values = value.split(" ");
        if (values.length != 3)
            return null;
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
