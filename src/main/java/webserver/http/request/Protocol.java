package webserver.http.request;

import java.util.Objects;

public class Protocol {
    private final String value;
    private final Version version;

    public Protocol(String value, Version version) {
        this.value = value;
        this.version = version;
    }

    public String value() {
        return value;
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Protocol protocol = (Protocol) o;
        return value.equals(protocol.value) && getVersion().equals(protocol.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, getVersion());
    }
}
