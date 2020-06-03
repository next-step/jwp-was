package http;

public class Protocol {
    String protocol;
    String version;

    public Protocol() {
    }

    public Protocol(String protocol) {
        String values[] = protocol.split("/");
        this.protocol = values[0];
        this.version = values[1];
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
