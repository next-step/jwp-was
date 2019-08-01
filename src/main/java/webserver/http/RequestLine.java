package webserver.http;

public class RequestLine {
    private final String version;
    private final String method;
    private final String path;

    public RequestLine(String method, String path, String version) {
        this.method = method;
        this.path = path;
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public static RequestLine parse(String requestLine) {
        String[] splitedRequestLine = requestLine.split(" ");
        String method = splitedRequestLine[0];
        String path = splitedRequestLine[1];
        String version = splitedRequestLine[2];
        return new RequestLine(method, path, version);
    }
}
