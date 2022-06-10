package webserver;

import java.util.Objects;

public class RequestLine {

    private final HttpMethod httpMethod;
    private final Uri uri;
    private final Protocol protocol;

    public RequestLine(HttpMethod httpMethod, Uri uri, Protocol protocol) {
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.protocol = protocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return httpMethod == that.httpMethod && Objects.equals(uri, that.uri) && Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, uri, protocol);
    }
}
