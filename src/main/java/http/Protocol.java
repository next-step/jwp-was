package http;

import java.util.Objects;

public class Protocol {

    private String protocol;
    private String version;

    private Protocol(String value) {
        String[] values = value.split("/");

        if(values.length != 2) {
            throw new IllegalArgumentException();
        }
        this.protocol = values[0];
        this.version = values[1];
    }

    private Protocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public static Protocol fromString(String value) {
        return new Protocol(value);
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
