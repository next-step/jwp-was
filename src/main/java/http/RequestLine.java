package http;

public class RequestLine {

    private final HttpMethod method;
    private final String path;
    private final Protocol protocol;

    public RequestLine(String method, String path, Protocol protocol) {
        this.method = HttpMethod.valueOf(method.toUpperCase());
        this.path = path;
        this.protocol = protocol;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public ProtocolType getProtocol() {
        return protocol.getProtocolType();
    }

    public String getVersion() {
        return protocol.getVersion();
    }
}
