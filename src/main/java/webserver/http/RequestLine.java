package webserver.http;

public class RequestLine {
    private final String method;
    private final String path;

    public RequestLine(String method, String path) {
        this.method = method;
        this.path = path;
    }

    public static RequestLine parse(String requestLine) {
        String[] values = requestLine.split(" ");
        return new RequestLine(values[0], values[1]);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
}
