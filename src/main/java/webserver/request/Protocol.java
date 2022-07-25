package webserver.request;

import java.util.Objects;

public class Protocol {

    private static final String PROTOCOL_DELIMITER = "/";
    private static final int INDEX_OF_TYPE = 0;
    private static final int INDEX_OF_VERSION = 1;
    private static final int VALID_SPLIT_LENGTH = 2;

    private final String type;
    private final String version;

    public Protocol(final String type, final String version) {
        this.type = type;
        this.version = version;
    }

    public static Protocol parse(final String protocol) {
        validateEmpty(protocol);

        final String[] tokens = protocol.split(PROTOCOL_DELIMITER);
        validateFormat(protocol, tokens);

        return of(tokens[INDEX_OF_TYPE], tokens[INDEX_OF_VERSION]);
    }

    public static Protocol of(final String http, final String version) {
        return new Protocol(http, version);
    }

    private static void validateEmpty(final String protocol) {
        if (protocol == null || protocol.isBlank()) {
            throw new IllegalArgumentException("빈 문자열은 파싱할 수 없습니다.");
        }
    }

    private static void validateFormat(final String protocol, final String[] tokens) {
        if (tokens.length != VALID_SPLIT_LENGTH) {
            throw new IllegalArgumentException("프로토콜 형식과 일치하지 않습니다 : " + protocol);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Protocol protocol = (Protocol) o;
        return Objects.equals(type, protocol.type) && Objects.equals(version, protocol.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, version);
    }
}
