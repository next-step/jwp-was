package http;

import java.util.Objects;

public class Protocol {
    private final String protocol;
    private final String version;

    public Protocol(String s) {
        String[] values = s.split("/");
        this.protocol = values[0];
        this.version = values[1];
    }

    public Protocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public static Protocol of(String protocol, String version) {
        return new Protocol(protocol, version);
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
