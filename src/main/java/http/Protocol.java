package http;

public class Protocol {

    private final ProtocolType protocolType;
    private final String version;

    public Protocol(String[] rawProtocolPart) {
        this.protocolType = ProtocolType.valueOf(rawProtocolPart[0].toUpperCase());
        this.version = rawProtocolPart[1];
    }

    public ProtocolType getProtocolType() {
        return protocolType;
    }

    public String getVersion() {
        return version;
    }
}
