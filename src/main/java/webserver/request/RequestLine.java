package webserver.request;

public class RequestLine {

    static final int INDEX_OF_METHOD = 0;
    static final int INDEX_OF_URL = 1;
    static final int INDEX_OF_VERSION = 2;

    private HttpMethod method;
    private HttpURI uri;
    private String version;

    private RequestLine(HttpMethod method, HttpURI uri, String version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public static RequestLine parse(String request) {
        String[] requests = request.split(" ");
        return new RequestLine(
                HttpMethod.valueOf(requests[INDEX_OF_METHOD]),
                HttpURI.of(requests[INDEX_OF_URL]),
                requests[INDEX_OF_VERSION]
        );
    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpURI getUri() {
        return uri;
    }

    public boolean matchPath(String path) {
        return uri.matchPath(path);
    }

    public String getPath() {
        return uri.getPath();
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + method +
                ", uri=" + uri +
                ", version='" + version + '\'' +
                '}';
    }
}
