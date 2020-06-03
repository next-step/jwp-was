package webserver;

import java.util.Objects;

public class Protocol {

    private String name;
    private String version;

    public Protocol(String protocol) {
        String[] split = protocol.split("/");
        this.name = split[0];
        this.version = split[1];
    }

    public Protocol(String name, String version) {
        this.name = name;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Protocol protocol = (Protocol) o;
        return Objects.equals(name, protocol.name) &&
                Objects.equals(version, protocol.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, version);
    }
}
