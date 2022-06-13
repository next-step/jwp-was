package webserver.http;

import org.apache.logging.log4j.util.Strings;

public class RequestLine {

    private static final int INDEX_OF_METHOD = 0;
    private static final int INDEX_OF_PATH = 1;
    private static final int INDEX_OF_HTTP_PROTOCOL = 2;
    private static final int LENGTH_OF_REQUEST_LINE = 3;

    private final HttpMethod method;
    private final Path path;
    private final HttpProtocol httpProtocol;

    public RequestLine(final String requestLine) {
        validateRequestLine(requestLine);
        final var splitRequestLine = splitRequestLine(requestLine);
        this.method = HttpMethod.valueOf(splitRequestLine[INDEX_OF_METHOD]);
        this.path = new Path(splitRequestLine[INDEX_OF_PATH]);
        this.httpProtocol = new HttpProtocol(splitRequestLine[INDEX_OF_HTTP_PROTOCOL]);
    }

    private void validateRequestLine(final String requestLine) {
        if (Strings.isBlank(requestLine)) {
            throw new IllegalArgumentException("RequestLine은 필수입니다.");
        }
    }

    private String[] splitRequestLine(final String requestLine) {
        final var splitRequestLine = requestLine.split(" ");
        if (splitRequestLine.length < LENGTH_OF_REQUEST_LINE) {
            throw new IllegalArgumentException(String.format("잘못된 RequestLine을 입력했습니다. 입력값: %s", requestLine));
        }
        return splitRequestLine;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path.getPath();
    }

    public String getProtocol() {
        return httpProtocol.getProtocol();
    }

    public String getVersion() {
        return httpProtocol.getVersion();
    }
}
