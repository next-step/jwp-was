package webserver.domain;

public class RequestLine {

    public static final int METHOD_INDEX = 0;
    public static final int URL_INDEX = 1;
    public static final int PROTOCOL_INDEX = 2;

    private final HttpMethod method;
    private final Url url;
    private final ProtocolVersion protocolVersion;

    public RequestLine(HttpMethod method, Url url, ProtocolVersion protocolVersion) {
        this.method = method;
        this.url = url;
        this.protocolVersion = protocolVersion;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Url getUrl() {
        return url;
    }

    public ProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }
}
