package webserver.http;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProtocolVersion {

    private static final Pattern PROTOCOL_VERSION_PATTERN = Pattern.compile("^\\d\\.\\d$");

    private static final String PROTOCOL_DELIMITER = "/";

    private static final String PROTOCOL_NAME = "HTTP";

    private static final int PROTOCOL_VALUES_LENGTH = 2;

    private static final int NAME_IDX = 0;

    private static final int VERSION_IDX = 1;

    private final String version;

    ProtocolVersion(String version) {
        validateVersion(version);
        this.version = version;
    }

    private void validateVersion(String version) {
        Matcher matcher = PROTOCOL_VERSION_PATTERN.matcher(version);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(String.format("[%s] 유효한 프로토콜 버전이 아님", version));
        }
    }

    static ProtocolVersion parseOf(String httpProtocol) {
        String[] protocolValues = httpProtocol.split(PROTOCOL_DELIMITER);

        if (protocolValues.length != PROTOCOL_VALUES_LENGTH || !protocolValues[NAME_IDX].equals(PROTOCOL_NAME)) {
            throw new IllegalArgumentException(String.format("[%s] 유효한 프로토콜 형식이 아님", httpProtocol));
        }

        return new ProtocolVersion(protocolValues[VERSION_IDX]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProtocolVersion protocol = (ProtocolVersion) o;
        return Objects.equals(version, protocol.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version);
    }
}
