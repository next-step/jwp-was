package webserver.request;


import utils.Assert;

import java.util.Objects;

final class Protocol {

    private static final String NAME_VERSION_DELIMITER = "/";

    private final String name;
    private final String version;

    private Protocol(String name, String version) {
        Assert.hasText(name, "'name' must not be blank");
        Assert.hasText(version, "'version' must not be blank");
        this.name = name;
        this.version = version;
    }

    static Protocol of(String name, String version) {
        return new Protocol(name, version);
    }

    static Protocol from(String string) {
        if (string == null || string.isBlank() || !string.contains(NAME_VERSION_DELIMITER)) {
            throw new IllegalArgumentException(String.format("'protocol'(%s) must not be null and have version", string));
        }
        return parse(string);
    }

    private static Protocol parse(String string) {
        String[] splitString = string.split(NAME_VERSION_DELIMITER);
        return new Protocol(splitString[0], splitString[1]);
    }

    String name() {
        return name;
    }

    String version() {
        return version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, version);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Protocol protocol = (Protocol) o;
        return Objects.equals(name, protocol.name) && Objects.equals(version, protocol.version);
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
