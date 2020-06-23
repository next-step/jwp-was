package http;

import java.util.Objects;

public class Protocol {
    private static final int MIN_PROTOCOL_SIZE = 2;
    private static final String SLASH = "/";

    private final String protocol;
    private final String version;

    private Protocol(final String protocol, final String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public static Protocol of(final String value) {
        final String[] values = value.split(SLASH);
        if (values.length < MIN_PROTOCOL_SIZE) {
            throw new IllegalArgumentException("Protocol must have protocol and version");
        }
        return new Protocol(values[0], values[1]);
    }

    public static Protocol of(final String protocol, final String version) {
        return new Protocol(protocol, version);
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
