package webserver;

public class RequestLine {

    private HttpMethod method;
    private String path;
    private String version;

    private RequestLine(String method, String path, String version) {
        this.method = HttpMethod.valueOf(method);
        this.path = path;
        this.version = version;
    }

    static RequestLine parse(String request) {
        String[] requests = request.split(" ");
        return new RequestLine(requests[0], requests[1], requests[2]);
    }

    HttpMethod getMethod() {
        return method;
    }

    String getPath() {
        return path;
    }

    String getVersion() {
        return version;
    }
}
