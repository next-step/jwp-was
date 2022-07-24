package webserver.request;

import java.util.Objects;

public class RequestLine {

    private static final int INDEX_OF_METHOD = 0;
    private static final int INDEX_OF_PATH = 1;
    private static final int INDEX_OF_PROTOCOL = 2;
    private static final String DELIMITER = " ";

    private final HttpMethod httpMethod;
    private final Path path;
    private final Protocol protocol;

    private RequestLine(final HttpMethod httpMethod, final Path path, final Protocol protocol) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.protocol = protocol;
    }

    public static RequestLine parse(final String requestLine) {
        validate(requestLine);

        final String[] tokens = requestLine.split(DELIMITER);

        return of(tokens[INDEX_OF_METHOD], tokens[INDEX_OF_PATH], tokens[INDEX_OF_PROTOCOL]);
    }

    private static void validate(final String requestLine) {
        if (requestLine == null || requestLine.isBlank()) {
            throw new IllegalArgumentException("빈 문자열은 파싱할 수 없습니다.");
        }
    }

    public static RequestLine of(final String httpMethod, final String path, final String protocol) {
        return new RequestLine(HttpMethod.of(httpMethod), Path.parse(path), Protocol.parse(protocol));
    }

    public boolean isGet() {
        return httpMethod.isGet();
    }

    public String getLocation() {
        return path.getLocation();
    }

    public QueryParameters getQueryParameters() {
        return path.getQueryString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RequestLine that = (RequestLine) o;
        return httpMethod == that.httpMethod && Objects.equals(path, that.path) && Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, path, protocol);
    }
}
