package http;

import java.util.Objects;

public class Protocol {
    private String protocol;
    private String version;

    public Protocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public Protocol(final String protocolAndVersion) {
        String[] values = protocolAndVersion.split("/");
        new Protocol(values[0], values[1]);
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Protocol protocol1 = (Protocol) o;
        return Objects.equals(getProtocol(), protocol1.getProtocol()) &&
                Objects.equals(getVersion(), protocol1.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProtocol(), getVersion());
    }
}
