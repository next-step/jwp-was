package webserver.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * 프로토콜 정보
 */
public class Protocol {
    public static final String DELIMITER = "/";
    public static final int PROTOCOL_INDEX = 0;
    public static final int VERSION_INDEX = 1;
    public static final String INVALID_PROTOCOL_INFO = "유효하지 않은 프로토콜 정보입니다. value: ";

    private final String value;
    private final Version version;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Protocol(@JsonProperty("protocol") String value, @JsonProperty("version") Version version) {
        this.value = value;
        this.version = version;
    }

    public static Protocol newInstance(String line) {
        String[] values = line.split(DELIMITER);

        if (!isSplinted(values)) {
            throw new IllegalArgumentException(INVALID_PROTOCOL_INFO + line);
        }

        return new Protocol(values[PROTOCOL_INDEX], Version.from(values[VERSION_INDEX]));
    }

    private static boolean isSplinted(String[] values) {
        return values.length == 2;
    }

    public String getValue() {
        return value;
    }

    public Version getVersion() {
        return version;
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
        return Objects.equals(value, protocol.value) && Objects.equals(version, protocol.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, version);
    }

    @Override
    public String toString() {
        return value + "/" + version.value();
    }
}
