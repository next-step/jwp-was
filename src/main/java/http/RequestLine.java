package http;

public class RequestLine {

    private final String method;
    private final String path;
    private final Protocol protocol;

    public RequestLine(String method, String path, String protocolText) {
        this.method = method;
        this.path = path;
        this.protocol = new Protocol(protocolText);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol.getName();
    }

    public String getVersion() {
        return protocol.getVersion();
    }
}
