package http;

import java.util.Objects;

public class Protocol {
    private static final String SLASH = "/";
    private final String protocol;
    private final String version;

    public Protocol(final String value) {
        final String[] values = value.split(SLASH);
        if (values.length < 2) {
            throw new IllegalArgumentException();
        }
        this.protocol = values[0];
        this.version = values[1];
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
