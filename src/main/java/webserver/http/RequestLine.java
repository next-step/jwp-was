package webserver.http;

public class RequestLine {

    private String method;
    private String path;

    public RequestLine(String method, String path) {
        this.method = method;
        this.path = path;
    }

    public static RequestLine parse(String rawRequestLine) {
        String[] requestLine = rawRequestLine.split(" ");
        return new RequestLine(requestLine[0], requestLine[1]);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
}
