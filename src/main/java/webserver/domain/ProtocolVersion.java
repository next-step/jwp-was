package webserver.domain;

public class ProtocolVersion {
    private static final String PROTOCOL_DELIMITER = "/";
    public static final int PROTOCOL_INDEX = 0;
    public static final int VERSION_INDEX = 1;

    private final Protocol protocol;
    private final HttpVersion version;


    public ProtocolVersion(Protocol protocol, HttpVersion version) {
        this.protocol = protocol;
        this.version = version;
    }

    public static ProtocolVersion parseFrom(final String protocolVersion){
        String[] splitProtocolVersion = protocolVersion.split(PROTOCOL_DELIMITER);

        return new ProtocolVersion(
                Protocol.valueOf(splitProtocolVersion[ProtocolVersion.PROTOCOL_INDEX]),
                HttpVersion.from(splitProtocolVersion[ProtocolVersion.VERSION_INDEX])
        );
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public HttpVersion getVersion() {
        return version;
    }
}
