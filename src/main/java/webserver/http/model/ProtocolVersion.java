package webserver.http.model;

import exception.IllegalHttpRequestException;

import java.util.Objects;

public class ProtocolVersion {
    private static final int VALID_PROTOCOL_VERSION_LENGTH = 2;

    private final Protocol protocol;
    private final Version version;

    public ProtocolVersion(String protocolAndVersion) {
        String[] protocolData = protocolAndVersion.split("/");
        if (protocolData.length != VALID_PROTOCOL_VERSION_LENGTH) {
            throw new IllegalHttpRequestException("protocolVersion에 들어가는 값은 protocol과 version만 포함되며 /는 하나만 사용됩니다.");
        }

        this.protocol = new Protocol(protocolData[0]);
        this.version = new Version(protocolData[1]);
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
        return Objects.equals(protocol, that.protocol) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, version);
    }
}
