package webserver.http;

import exception.InvalidProtocolException;

import java.util.Arrays;
import java.util.Objects;

public class Protocol {

    private static final String DELIMITER = "/";
    private static final int PROTOCOL_SUITE_SIZE = 2;
    private static final int INDEX_TYPE = 0;
    private static final int INDEX_VERSION = 1;

    private final Type type;
    private final Version version;

    public Protocol(String suite) {
        final String[] suiteElements = suite.split(DELIMITER);
        validateProtocol(suiteElements);

        this.type = Type.from(suiteElements[INDEX_TYPE]);
        this.version = Version.from(suiteElements[INDEX_VERSION]);
    }

    public Protocol(Type type, Version version) {
        this.type = type;
        this.version = version;
    }

    private void validateProtocol(String[] suiteElements) {
        if (suiteElements.length != PROTOCOL_SUITE_SIZE) {
            throw new InvalidProtocolException(Arrays.toString(suiteElements));
        }
    }

    public Type getType() {
        return type;
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Protocol protocol = (Protocol) o;
        return type == protocol.type && version == protocol.version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, version);
    }

    @Override
    public String toString() {
        return type.name() + "/" + version.getLabel();
    }
}
