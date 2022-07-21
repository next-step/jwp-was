package webserver.request;

import java.util.Objects;

public class ProtocolVersion {
    private Protocol protocol;
    private Version version;

    private static final String PROTOCOL_VERSION_SPLITTER = "/";

    public ProtocolVersion(Protocol protocol, Version version) {
        this.protocol = protocol;
        this.version = version;
    }

    public static ProtocolVersion findProtocolAndVersion(String protocolVersion) {
        String[] str = protocolVersion.split(PROTOCOL_VERSION_SPLITTER);
        return new ProtocolVersion(new Protocol(str[0]), new Version(str[1]));
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProtocolVersion that = (ProtocolVersion) o;
        return Objects.equals(protocol, that.protocol) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, version);
    }
}
