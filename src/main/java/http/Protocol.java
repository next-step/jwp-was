package http;

public class Protocol {
    private final String protocol;
    private final String version;

    public Protocol(String protocolAndVersion) {
        String[] values = parseProtocolAndVersion(protocolAndVersion);
        this.protocol = values[0];
        this.version = values[1];
    }

    private String[] parseProtocolAndVersion(String protocolAndVersion) {
        return protocolAndVersion.split("/");
    }
}
