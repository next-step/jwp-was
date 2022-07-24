package webserver.http;

import java.util.List;

public class RequestLine {
    private static final String BLANK_DELIMITER = " ";
    private static final String SLASH_DELIMITER = "/";
    private static final int METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int PROTOCOL_INDEX = 0;
    private static final int VERSION_INDEX = 1;
    private static final int PROTOCOL_AND_VERSION_INDEX = 2;
    private static final int REQUEST_PARSING_ELEMENT_NUMBER = 3;
    private static final int PROTOCOL_AND_VERSION_PARSING_ELEMENT_NUMBER = 2;

    private Method method;
    private Path path;
    private String protocol;
    private String version;

    RequestLine(Method method, Path path, String protocol, String version) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    public static RequestLine parse(String requestLine) {
        validateRequestLine(requestLine);
        String[] elements = requestLine.split(BLANK_DELIMITER);
        validateElementsLength(elements.length);

        Method method = Method.valueOf(elements[METHOD_INDEX]);
        Path path = Path.parse(elements[PATH_INDEX]);

        String[] protocolAndVersion = elements[PROTOCOL_AND_VERSION_INDEX].split(SLASH_DELIMITER);
        validateProtocolAndVersionLength(protocolAndVersion.length);

        String protocol = protocolAndVersion[PROTOCOL_INDEX];
        String version = protocolAndVersion[VERSION_INDEX];
        validateProtocol(protocol);
        validateVersion(version);

        return new RequestLine(method, path, protocol, version);
    }

    private static void validateRequestLine(String requestLine) {
        if (requestLine == null || requestLine.isEmpty()) {
            throw new IllegalArgumentException("요청된 HTTP RequestLine 은 null 이거나 비어있을 수 없습니다.");
        }
    }

    private static void validateElementsLength(int length) {
        if (length != REQUEST_PARSING_ELEMENT_NUMBER) {
            throw new IllegalArgumentException(String.format("요청된 HTTP RequestLine 을 공백으로 파싱한 정보 갯수가 [method] + [path] + [protocol/version]로 총 3이여야 합니다. 현재 파싱된 정보 갯수 : %d", length));
        }
    }

    private static void validateProtocolAndVersionLength(int length) {
        if (length != PROTOCOL_AND_VERSION_PARSING_ELEMENT_NUMBER) {
            throw new IllegalArgumentException(String.format("[protocol/version] 을 '/'으로 파싱한 정보 갯수가 [protocol] + [version]로 총 2이여야 합니다. 현재 파싱된 정보 갯수 : %d", length));
        }
    }

    private static void validateProtocol(String protocol) {
        if (!protocol.equals("HTTP")) {
            throw new IllegalArgumentException(String.format("요청된 HTTP RequestLine 의 protocol 는 'HTTP' 여야 합니다. 현재 입력된 protocol : %s", protocol));
        }
    }

    private static void validateVersion(String version) {
        try {
            Double.parseDouble(version);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(String.format("%s는 숫자가 아닙니다.", version));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestLine that = (RequestLine) o;

        if (method != that.method) return false;
        if (!path.equals(that.path)) return false;
        if (!protocol.equals(that.protocol)) return false;
        return version.equals(that.version);
    }

    @Override
    public int hashCode() {
        int result = method.hashCode();
        result = 31 * result + path.hashCode();
        result = 31 * result + protocol.hashCode();
        result = 31 * result + version.hashCode();
        return result;
    }
}
