package model;

import java.util.Map;

public class RequestLine {
    private final HttpMethod method;
    private final Path path;
    private final String protocol;
    private final String version;

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

    public Map<String, String> getParameters() {
        return path.getParameters();
    }

    public boolean isMatchHttpMethod(HttpMethod method, Path path) {
        return this.method.equals(method) && this.path.equalPath(path);
    }
}
