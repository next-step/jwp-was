package webserver.domain;

public class RequestLine {

    public static final int METHOD_INDEX = 0;
    public static final int PATH_INDEX = 1;
    public static final int PROTOCOL_INDEX = 2;

    private final HttpMethod method;
    private final Path path;
    private final ProtocolVersion protocolVersion;

    public RequestLine(HttpMethod method, Path path, ProtocolVersion protocolVersion) {
        this.method = method;
        this.path = path;
        this.protocolVersion = protocolVersion;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Path getPath() {
        return path;
    }

    public ProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }
}
