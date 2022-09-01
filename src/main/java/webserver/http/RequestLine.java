package webserver.http;

import java.util.Objects;

public class RequestLine {
    private static final int VALID_NUMBER_OF_PROPERTIES = 3;
    private static final String PROPERTIES_DELIMITER = " ";

    private HttpMethod method;
    private Path path;
    private Protocol protocol;

    public RequestLine(HttpMethod method, Path path, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public static RequestLine from(String line) {
        String[] tokens = validTokens(line);
        return new RequestLine(HttpMethod.valueOf(tokens[0]), new Path(tokens[1]), Protocol.from(tokens[2]));
    }

    private static String[] validTokens(String httpRequest) {
        String[] tokens = httpRequest.split(PROPERTIES_DELIMITER);
        validate(tokens);
        return tokens;
    }

    private static void validate(String[] properties) {
        if (properties.length != VALID_NUMBER_OF_PROPERTIES) {
            throw new IllegalArgumentException(String.format("필요한 속성의 개수[%d]를 만족하지 않습니다.", VALID_NUMBER_OF_PROPERTIES));
        }
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String path() {
        return this.path.getPath();
    }

    public String query() {
        return this.path.getQueryString();
    }

    public Protocol getProtocol() {
        return protocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return method == that.method && Objects.equals(path, that.path) && Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, protocol);
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + method +
                ", path=" + path +
                ", protocol=" + protocol +
                '}';
    }
}
