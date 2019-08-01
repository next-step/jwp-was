package webserver.http;

public class RequestLine {

    private static final String REQUEST_SPLITTER =" ";

    private String method;
    private String path;
    private String version;

    private RequestLine(String method, String path, String version) {
        this.method = method;
        this.path = path;
        this.version = version;
    }

    public static RequestLine parse(String request) {
        String[] requests = request.split(REQUEST_SPLITTER);

        return new RequestLine(requests[0], requests[1], requests[2]);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }
}
