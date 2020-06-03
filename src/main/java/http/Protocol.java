package http;

import java.util.Objects;

public class Protocol {

    private String protocol;
    private String version;

    private static final String PROTOCOL_TOKENIZER = "/";

    public Protocol(String protocolAndVersion) {
        String[] result = protocolAndVersion.split(PROTOCOL_TOKENIZER);
        this.protocol = result[0];
        this.version = result[1];
    }

    public Protocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Protocol protocol1 = (Protocol) o;
        return Objects.equals(protocol, protocol1.protocol) &&
                Objects.equals(version, protocol1.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, version);
    }
}
