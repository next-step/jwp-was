package http.common;

import java.util.Objects;

public class ProtocolAndVersion {

    private String protocol;
    private String version;

    private static final String PROTOCOL_TOKENIZER = "/";

    public ProtocolAndVersion(String protocolAndVersion) {
        String[] result = protocolAndVersion.split(PROTOCOL_TOKENIZER);
        this.protocol = result[0];
        this.version = result[1];
    }

    public ProtocolAndVersion(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProtocolAndVersion protocolAndVersion1 = (ProtocolAndVersion) o;
        return Objects.equals(protocol, protocolAndVersion1.protocol) &&
                Objects.equals(version, protocolAndVersion1.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, version);
    }
}
