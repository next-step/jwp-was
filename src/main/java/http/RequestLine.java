package http;

import utils.StringUtils;


import java.util.Objects;

public class RequestLine {
    private static final String SPACE_DELIMITER = " ";
    public static final String REQUEST_LINE_IS_INVALID = "request line is invalid.";

    private final HttpMethod method;
    private final Uri uri;
    private final Protocol protocol;

    private RequestLine(HttpMethod method, Uri uri, Protocol protocol) {
        this.method = method;
        this.uri = uri;
        this.protocol = protocol;
    }

    public static RequestLine from(String fullRequestLine) {
        if (StringUtils.isEmpty(fullRequestLine)) {
            throw new IllegalArgumentException(REQUEST_LINE_IS_INVALID);
        }

        String[] values = fullRequestLine.split(SPACE_DELIMITER);
        if (values.length != 3) {
            throw new IllegalArgumentException(REQUEST_LINE_IS_INVALID);
        }

        return new RequestLine(HttpMethod.valueOf(values[0]), Uri.from(values[1]), Protocol.from(values[2]));
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
                protocol == that.protocol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, uri, protocol);
    }

    public String getPath() {
        return this.uri.getPath();
    }

    public QueryString getQueryString() {
        return this.uri.getQueryString();
    }

    public HttpMethod getMethod() {
        return this.method;
    }
}
