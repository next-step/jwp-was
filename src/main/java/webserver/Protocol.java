package webserver;

import java.util.Objects;

public class Protocol {
    public static final String PROPERTIES_DELIMITER = "/";
    private String name;
    private String version;

    private Protocol(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public static Protocol from(String protocol) {
        String properties[] = protocol.split(PROPERTIES_DELIMITER);
        return new Protocol(properties[0], properties[1]);
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
        if (o == null || getClass() != o.getClass()) return false;
        Protocol protocol = (Protocol) o;
        return Objects.equals(name, protocol.name) && Objects.equals(version, protocol.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, version);
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
