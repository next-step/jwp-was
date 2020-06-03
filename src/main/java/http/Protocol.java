package http;

public class Protocol {

    private final String protocol;
    private final String version;

    public Protocol(String protocolAndVersion) {
        String[] values = protocolAndVersion.split("/");
        this.protocol = values[0];
        this.version = values[1];
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getVersion() {
        return this.version;
    }

}
