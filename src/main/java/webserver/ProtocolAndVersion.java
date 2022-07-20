package webserver;

public class ProtocolAndVersion {
    private final String protocol;
    private final String version;

    public ProtocolAndVersion(String protocolAndVersion) {
        this.protocol = protocolAndVersion.split("/")[0];
        this.version = protocolAndVersion.split("/")[1];
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
