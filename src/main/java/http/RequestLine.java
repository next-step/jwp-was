package http;

import java.util.Map;

public class RequestLine {
    private HttpMethod method;
    private String path;
    private String protocol;
    private String version;
    private Map<String, Object> queryString;

    public RequestLine() {
    }

    public RequestLine(HttpMethod method, String path, String protocol, String version, Map<String, Object> queryString) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
        this.queryString = queryString;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, Object> getQueryString() {
        return queryString;
    }

}
