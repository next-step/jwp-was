package http;

import java.util.Objects;

public class Protocol {
    private static final String SLASH_DELIMITER = "/";

    private final String name;
    private final String version;

    private Protocol(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public static Protocol from(String fullProtocol) {
        String[] splittedProtocol = fullProtocol.split(SLASH_DELIMITER);
        return new Protocol(splittedProtocol[0], splittedProtocol[1]);
    }

    public static Protocol of(String name, String version) {
        return new Protocol(name, version);
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
