package http;

import java.util.Objects;

public class RequestLine {
    private static final String SPACE_DELIMITER = " ";

    private final HttpMethod method;
    private final String path;
    private final Protocol protocol;

    private RequestLine(HttpMethod method, String path, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public static RequestLine from(String fullRequestLine) {
        String[] splittedRequestLine = fullRequestLine.split(SPACE_DELIMITER);
        return new RequestLine(HttpMethod.valueOf(splittedRequestLine[0]), splittedRequestLine[1], Protocol.from(splittedRequestLine[2]));
    }

    public static RequestLine of(String method, String path, String protocol, String version) {
        return new RequestLine(HttpMethod.valueOf(method), path, Protocol.of(protocol, version));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) &&
                Objects.equals(path, that.path) &&
                Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, protocol);
    }
}
