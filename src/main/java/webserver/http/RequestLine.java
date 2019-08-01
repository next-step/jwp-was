package webserver.http;

public class RequestLine {

    private final String method;
    private final String path;

    public RequestLine(final String method,
                       final String path) {
        this.method = method;
        this.path = path;
    }

    public static RequestLine parse(final String rawRequestLine) {
        final String[] splitRequestLine = rawRequestLine.split(" ");

        return new RequestLine(splitRequestLine[0], splitRequestLine[1]);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
}
