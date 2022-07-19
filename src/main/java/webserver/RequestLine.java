package webserver;

import org.springframework.util.StringUtils;

public class RequestLine {

    private static final int INDEX_OF_METHOD = 0;
    private static final int INDEX_OF_PATH = 1;
    private static final int INDEX_OF_PROTOCOL_AND_VERSION = 2;
    private static final int INDEX_OF_PROTOCOL = 0;
    private static final int INDEX_OF_VERSION = 1;
    private static final String DELIMITER = " ";
    private static final String PROTOCOL_DELIMITER = "/";
    private static final String EMPTY_QUERY_STRING = "";


    private final HttpMethod method;
    private final String path;
    private final String protocol;
    private final String version;

    private RequestLine(final HttpMethod method, final String path, final String protocol, final String version) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    public static RequestLine parse(final String requestLine) {
        if (!StringUtils.hasText(requestLine)) {
            throw new IllegalArgumentException("빈 문자열은 파싱할 수 없습니다.");
        }

        final String[] tokens = requestLine.split(DELIMITER);
        final HttpMethod method = HttpMethod.of(tokens[INDEX_OF_METHOD]);
        final String path = tokens[INDEX_OF_PATH];
        final String[] protocolAndVersion = tokens[INDEX_OF_PROTOCOL_AND_VERSION].split(PROTOCOL_DELIMITER);
        final String protocol = protocolAndVersion[INDEX_OF_PROTOCOL];
        final String version = protocolAndVersion[INDEX_OF_VERSION];

        return new RequestLine(method, path, protocol, version);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    public String getQueryString() {
        final String[] tokens = path.split("\\?");
        if (hasQueryString(tokens)) {
            return tokens[1];
        }
        return EMPTY_QUERY_STRING;
    }

    private boolean hasQueryString(final String[] tokens) {
        return tokens.length == 2;
    }
}
