package http.requests;

public class Protocol {

    public static final String PROTOCOL_DELIMITER = "/";
    private final String protocol;
    private final String version;

    public Protocol(String protocolString) {
        final String[] protocolAndVersion = protocolString.split(PROTOCOL_DELIMITER);
        this.protocol = protocolAndVersion[0];
        this.version = protocolAndVersion[1];
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
