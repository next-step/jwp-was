package domain;

import java.util.Objects;

public class HttpProtocol {
    private static final String PROTOCOL_DELIMITER = "/";

    private final String protocol;
    private final String version;

    public HttpProtocol(String protocolSpec) {
        final String[] splitProtocolSpec = protocolSpec.split(PROTOCOL_DELIMITER);
        protocol = splitProtocolSpec[0];
        version = splitProtocolSpec[1];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpProtocol that = (HttpProtocol) o;
        return Objects.equals(protocol, that.protocol) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, version);
    }
}
