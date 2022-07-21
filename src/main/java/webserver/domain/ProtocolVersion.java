package webserver.domain;

public class ProtocolVersion {
    public static final int PROTOCOL_INDEX = 0;
    public static final int VERSION_INDEX = 1;

    private final Protocol protocol;
    private final HttpVersion version;

    public ProtocolVersion(Protocol protocol, HttpVersion version) {
        this.protocol = protocol;
        this.version = version;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public HttpVersion getVersion() {
        return version;
    }
}
