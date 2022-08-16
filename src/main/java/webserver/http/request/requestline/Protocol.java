package webserver.http.request.requestline;

public class Protocol {
    private static final String SLASH_DELIMITER = "/";
    private static final String HTTP_PROTOCOL = "HTTP";
    private static final int PROTOCOL_AND_VERSION_PARSING_ELEMENT_NUMBER = 2;
    private static final int PROTOCOL_INDEX = 0;
    private static final int VERSION_INDEX = 1;

    private ProtocolType protocolType;
    private Version version;

    public Protocol(ProtocolType protocolType, Version version) {
        validate(protocolType, version);
        this.protocolType = protocolType;
        this.version = version;
    }

    private static void validate(ProtocolType protocolType, Version version) {
        validateProtocolType(protocolType);
        validateVersion(version);
    }

    private static void validateProtocolType(ProtocolType protocolType) {
        if (protocolType == null) {
            throw new IllegalArgumentException("프로토콜 타입은 null 일 수 없습니다.");
        }
    }

    private static void validateVersion(Version version) {
        if (version == null) {
            throw new IllegalArgumentException("프로토콜 버전은 null 일 수 없습니다.");
        }
    }

    public static Protocol ofHttpV11() {
        return new Protocol(ProtocolType.HTTP, Version.ONE_ONE);
    }

    public static Protocol parse(String protocolString) {
        validateProtocolString(protocolString);
        String[] protocolAndVersion = protocolString.split(SLASH_DELIMITER);
        validateProtocolAndVersionLength(protocolAndVersion.length);

        String protocol = protocolAndVersion[PROTOCOL_INDEX];
        Version version = Version.valueOfVersion(protocolAndVersion[VERSION_INDEX]);
        validateProtocol(protocol);

        return new Protocol(ProtocolType.valueOf(protocol), version);
    }

    private static void validateProtocolString(String protocolString) {
        if (protocolString == null || protocolString.isEmpty()) {
            throw new IllegalArgumentException("요청된 HTTP RequestLine 의 protocol 는 비어있거나 null 일 수 없습니다.");
        }
    }

    private static void validateProtocol(String protocol) {
        if (!protocol.equals(HTTP_PROTOCOL)) {
            throw new IllegalArgumentException(String.format("요청된 HTTP RequestLine 의 protocol 는 'HTTP' 여야 합니다. 현재 입력된 protocol : %s", protocol));
        }
    }

    private static void validateProtocolAndVersionLength(int length) {
        if (length != PROTOCOL_AND_VERSION_PARSING_ELEMENT_NUMBER) {
            throw new IllegalArgumentException(String.format("[protocol/version] 을 '/'으로 파싱한 정보 갯수가 [protocol] + [version]로 총 2이여야 합니다. 현재 파싱된 정보 갯수 : %d", length));
        }
    }

    public String protocol() {
        return this.protocolType + SLASH_DELIMITER + this.version.version();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Protocol protocol1 = (Protocol) o;

        if (!protocolType.equals(protocol1.protocolType)) return false;
        return version == protocol1.version;
    }

    @Override
    public int hashCode() {
        int result = protocolType.hashCode();
        result = 31 * result + version.hashCode();
        return result;
    }
}
