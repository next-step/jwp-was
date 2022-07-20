package webserver;

import org.springframework.util.StringUtils;

public class RequestLine {

    private static final int INDEX_OF_METHOD = 0;
    private static final int INDEX_OF_PATH = 1;
    private static final int INDEX_OF_PROTOCOL = 2;
    private static final String DELIMITER = " ";

    private final HttpMethod method;
    private final Path path;
    private final Protocol protocol;

    private RequestLine(final HttpMethod method, final Path path, final Protocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public static RequestLine parse(final String requestLine) {
        if (!StringUtils.hasText(requestLine)) {
            throw new IllegalArgumentException("빈 문자열은 파싱할 수 없습니다.");
        }

        final String[] tokens = requestLine.split(DELIMITER);
        final HttpMethod method = HttpMethod.of(tokens[INDEX_OF_METHOD]);
        final Path path = Path.from(tokens[INDEX_OF_PATH]);
        final Protocol protocol = Protocol.parse(tokens[INDEX_OF_PROTOCOL]);

        return new RequestLine(method, path, protocol);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path.getLocation();
    }

    public String getProtocol() {
        return protocol.getType();
    }

    public String getVersion() {
        return protocol.getVersion();
    }

    public String getQueryString() {
        return path.getQueryString();
    }

}
