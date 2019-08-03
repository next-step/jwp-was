package webserver.http;

public class RequestLine {
    private String method;
    private URI uri;
    private String version;

    private RequestLine(String method, String path, String version) {
        this.method = method;
        this.uri = URI.parse(path);
        this.version = version;
    }

    public static RequestLine parse(String path) {
        String[] values = path.split(" ");
        return new RequestLine(values[0], values[1], values[2]);
    }

    public String getMethod() {
        return this.method;
    }

    public URI getUri() {
        return this.uri;
    }
}
