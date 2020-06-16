package http;

import java.util.Objects;

public class Protocol {
    private final String protocol;
    private final String version;

    public Protocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public Protocol(String protocol) {
        this.protocol = protocol;
        this.version = "1.1";
    }

    public static Protocol of(String value) {
        if (value == null)
            return null;
        String[] values = value.split("/");
        if (values.length == 2)
            return new Protocol(values[0], values[1]);
        return new Protocol(values[0]);
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
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
