package http;

import java.util.Map;

public class HttpProtocol {
    private final String method;
    private final String path;
    private final String protocol;
    private final String version;
    private final Map<String, String> header;

    public HttpProtocol(String method, String path, String protocol, String version, Map<String, String> header) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
        this.header = header;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getHeader() {
        return header;
    }
}
