package http;

import java.util.Objects;

public class Protocol {
    private final String protocol;
    private final String version;

    public Protocol(final String protocolAndVersion) {
        String[] protocols = protocolAndVersion.split("/");
        validateByLength(protocols);
        this.protocol = protocols[0];
        this.version = protocols[1];
    }

    private void validateByLength(String[] protocols) {
        if (protocols.length < 2) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }
    }



    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Protocol protocol1 = (Protocol) o;
        return Objects.equals(protocol, protocol1.protocol) &&
                Objects.equals(version, protocol1.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, version);
    }
}
