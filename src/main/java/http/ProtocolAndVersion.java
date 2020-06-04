package http;

public class ProtocolAndVersion {
    private Protocol protocol;
    private String version;

    public ProtocolAndVersion(String strProtocol) {

    }

    public ProtocolAndVersion(Protocol protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }
}
