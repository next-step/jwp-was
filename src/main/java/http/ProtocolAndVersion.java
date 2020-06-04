package http;

import java.util.Objects;

public class ProtocolAndVersion {
    private Protocol protocol;
    private String version;

    public ProtocolAndVersion(String strProtocol) {
        String[] split = strProtocol.split("/");
        protocol = Protocol.valueOf(split[0]);
        version = split[1];
    }

    public ProtocolAndVersion(Protocol protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProtocolAndVersion that = (ProtocolAndVersion) o;
        return protocol == that.protocol &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, version);
    }
}
