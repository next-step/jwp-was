package http;

public class Protocol {

    private final String protocol;
    private final String version;

    public Protocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
