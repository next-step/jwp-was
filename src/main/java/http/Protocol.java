package http;

import http.Const.HttpConst;

import java.util.Objects;

public class Protocol {
    private final String protocol;
    private final String version;

    public Protocol(String protocolAndVersion) {
        String[] values = protocolAndVersion.split(HttpConst.PROTOCOL_VERSION_SEPARATOR);
        if (values.length != 2) {
            throw new IllegalArgumentException("Invalid format : " + protocolAndVersion);
        }
        this.protocol = values[0];
        this.version = values[1];
    }

    public Protocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getVersion() {
        return this.version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Protocol)) return false;
        Protocol protocol1 = (Protocol) o;
        return Objects.equals(getProtocol(), protocol1.getProtocol()) &
                Objects.equals(getVersion(), protocol1.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProtocol(), getVersion());
    }
}
