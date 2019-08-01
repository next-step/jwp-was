package webserver;

public class RequestLine {

    private String method;
    private String path;
    private String version;

    private RequestLine(String method, String path, String version) {
        this.method = method;
        this.path = path;
        this.version = version;
    }

    static RequestLine parse(String request) {
        String[] requests = request.split(" ");
        return new RequestLine(requests[0], requests[1], requests[2]);
    }

    String getMethod() {
        return new String(method);
    }

    String getPath() {
        return new String(path);
    }

    String getVersion() {
        return new String(version);
    }
}
