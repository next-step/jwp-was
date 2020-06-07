package http;

import java.util.Objects;

public class RequestLine {
    private static final String SPACE_DELIMITER = " ";

    private final HttpMethod method;
    private final Uri uri;
    private final Protocol protocol;

    private RequestLine(HttpMethod method, Uri uri, Protocol protocol) {
        this.method = method;
        this.uri = uri;
        this.protocol = protocol;
    }

    public static RequestLine from(String fullRequestLine) {
        String[] splittedRequestLine = fullRequestLine.split(SPACE_DELIMITER);
        return new RequestLine(HttpMethod.valueOf(splittedRequestLine[0]), Uri.from(splittedRequestLine[1]), Protocol.from(splittedRequestLine[2]));
    }

    public static RequestLine of(String method, String uri, String protocol, String version) {
        return new RequestLine(HttpMethod.valueOf(method), Uri.from(uri), Protocol.of(protocol, version));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return method == that.method &&
                Objects.equals(uri, that.uri) &&
                Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, uri, protocol);
    }
}
