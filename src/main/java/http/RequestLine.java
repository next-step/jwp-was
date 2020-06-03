package http;

import java.util.Map;

public class RequestLine {

    private HttpMethod method;
    private String path;
    private Protocol protocol;
    private QueryString queryString;

    public RequestLine(HttpMethod method, String path, Protocol protocol, QueryString queryString) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.queryString = queryString;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return this.protocol.getProtocol();
    }

    public String getVersion() {
        return this.protocol.getVersion();
    }

    public Map<String, String> getParameterMap() {
        return queryString.getParameterMap();
    }

    public String getParameter(String key) {
        return queryString.getParameter(key);
    }

}
