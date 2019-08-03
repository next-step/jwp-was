package webserver.http;

public class RequestLine {
    private String method;
    private URI uri;

    private RequestLine(String method, String path) {
        this.method = method;
        this.uri = URI.parse(path);
    }

    public static RequestLine parse(String path) {
        String[] values = path.split(" ");
        return new RequestLine(values[0], values[1]);
    }

    public String getMethod() {
        return this.method;
    }

    public URI getUri() {
        return this.uri;
    }
}
