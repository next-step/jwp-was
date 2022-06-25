package webserver.domain.http;

import java.util.Objects;

public class RequestLine {

    private final static String SPACE_DELIMITER = " ";

    private final HttpMethod httpMethod;
    private final Uri uri;
    private final Protocol protocol;

    public RequestLine(HttpMethod httpMethod, Uri uri, Protocol protocol) {
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.protocol = protocol;
    }

    public static RequestLine from(String input) {
        String[] splitInput = input.split(SPACE_DELIMITER);

        return new RequestLine(HttpMethod.valueOf(splitInput[0]),
                Uri.from(splitInput[1]),
                Protocol.from(splitInput[2]));
    }

    public Uri getUri() {
        return uri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
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

    @Override
    public String toString() {
        return "RequestLine{" +
                "httpMethod=" + httpMethod +
                ", uri=" + uri +
                ", protocol=" + protocol +
                '}';
    }
}
