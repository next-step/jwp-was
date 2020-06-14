package http.request;

import java.util.Objects;

public class Protocol {
    private static final String SEPARATOR = "/";
    private static final int MIN_SIZE = 2;
    private static final int PROTOCOL_INDEX = 0;
    private static final int VERSION_INDEX = 1;

    private final String protocol;
    private final String version;

    public Protocol(final String protocolAndVersion) {
        String[] protocols = protocolAndVersion.split(SEPARATOR);
        validateByLength(protocols);
        this.protocol = protocols[PROTOCOL_INDEX];
        this.version = protocols[VERSION_INDEX];
    }

    private void validateByLength(String[] protocols) {
        if (protocols.length < MIN_SIZE) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }
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
        return Objects.equals(protocol, protocol1.protocol) &&
                Objects.equals(version, protocol1.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, version);
    }
}
