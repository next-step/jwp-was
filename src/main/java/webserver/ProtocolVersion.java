package webserver;

public class ProtocolVersion {

    private static final String REQUEST_PROTOCOL_VERSION_DELIMITER = "/";

    private static final int PROTOCOL_INDEX = 0;
    private static final int VERSION_INDEX = 1;

    private String protocol;
    private String version;

    protected ProtocolVersion() {

    }

    private ProtocolVersion(final String protocol, final String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public static ProtocolVersion from(final String requestProtocolVersion) {
        String[] splitRequestProtocolVersion = requestProtocolVersion.split(REQUEST_PROTOCOL_VERSION_DELIMITER);

        return new ProtocolVersion(splitRequestProtocolVersion[PROTOCOL_INDEX], splitRequestProtocolVersion[VERSION_INDEX]);
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getVersion() {
        return this.version;
    }
}
