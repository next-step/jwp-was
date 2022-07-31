package model;

public class RequestLine {
    private HttpMethod method;
    private Path path;
    private String protocol;
    private String version;

    private RequestLine(HttpMethod method, Path path, String protocol, String version) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    public static RequestLine of(HttpMethod method, Path path, String[] protocolAndVersion) {
        return new RequestLine(method, path, protocolAndVersion[0], protocolAndVersion[1]);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path.getPath();
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    public String getParameters() {
        return path.getParameters();
    }
}
