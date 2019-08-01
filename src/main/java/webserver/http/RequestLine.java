package webserver.http;

public class RequestLine {
    private String method;
    private String path;

    private RequestLine(String method, String path) {
        this.method = method;
        this.path = path;
    }

    public static RequestLine parse(String requestLine) {
        validate(requestLine);

        String[] parsedRequestLine = requestLine.split(" ");
        return new RequestLine(parsedRequestLine[0], parsedRequestLine[1]);
    }

    private static void validate(String requestLine) {
        if (requestLine == null || requestLine.length() == 0) {
            throw new IllegalArgumentException();
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
}
