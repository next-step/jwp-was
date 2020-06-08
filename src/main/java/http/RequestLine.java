package http;

import java.util.Map;

public class RequestLine {
    private final HttpMethod method;
    private final String path;
    private final Protocol protocol;
    private final QueryString queryString;

    public RequestLine(final HttpMethod httpMethod, final String path, final QueryString queryString, final Protocol protocol) {
        this.method = httpMethod;
        this.path = path;
        this.queryString = queryString;
        this.protocol = protocol;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol.getProtocol();
    }

    public String getVersion() {
        return protocol.getVersion();
    }

    public String getParameter(String key) {
        return queryString.getParameter(key);
    }

    public Map<String, String> getParameters() {
        return queryString.getParameters();
    }
}
