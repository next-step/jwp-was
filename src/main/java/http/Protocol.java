package http;

import java.util.Objects;

public class Protocol {
    private String name;
    private String version;

    public Protocol(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public static Protocol of(final String protocolAndVersion) {
        String[] values = protocolAndVersion.split("/");
        return new Protocol(values[0], values[1]);
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Protocol)) return false;
        Protocol protocol = (Protocol) o;
        return Objects.equals(getName(), protocol.getName()) &&
                Objects.equals(getVersion(), protocol.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getVersion());
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
