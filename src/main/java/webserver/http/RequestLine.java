package webserver.http;

public class RequestLine {

    private String requestLine;
    private String method;
    private String path;

    public static RequestLine parse(String requestLine) {
        final String[] requestLines = requestLine.split(" ");

        return new RequestLine(requestLine, requestLines[0], requestLines[1]);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    private RequestLine(String requestLine, String method, String path) {
        this.requestLine = requestLine;
        this.method = method;
        this.path = path;
    }
}
