package webserver;

import org.springframework.util.StringUtils;

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
        validateNullAndEmpty(protocol);

        final String[] tokens = protocol.split(PROTOCOL_DELIMITER);
        validateProtocolFormat(protocol, tokens);

        return new Protocol(tokens[INDEX_OF_TYPE], tokens[INDEX_OF_VERSION]);
    }

    private static void validateNullAndEmpty(final String protocol) {
        if (!StringUtils.hasText(protocol)) {
            throw new IllegalArgumentException("빈 문자열은 파싱할 수 없습니다.");
        }
    }

    private static void validateProtocolFormat(final String protocol, final String[] tokens) {
        if (tokens.length != VALID_SPLIT_LENGTH) {
            throw new IllegalArgumentException("프로토콜 형식과 일치하지 않습니다 : " + protocol);
        }
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }
}
