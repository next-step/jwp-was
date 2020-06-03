package http;

public class RequestLine {

    private final HttpMethod method;
    private final String path;
    private final ProtocolType protocol;
    private final String version;

    public RequestLine(String method, String path, String protocol, String version) {
        this.method = HttpMethod.valueOf(method.toUpperCase());
        this.path = path;
        this.protocol = ProtocolType.valueOf(protocol.toUpperCase());
        this.version = version;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public ProtocolType getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
