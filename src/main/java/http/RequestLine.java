package http;

import utils.StringUtils;


import java.util.Objects;

public class RequestLine {
    private static final String SPACE_DELIMITER = " ";
    public static final String REQUEST_LINE_IS_INVALID = "request line is invalid.";

    private final HttpMethod method;
    private final Uri uri;
    private final HttpProtocol httpProtocol;

    private RequestLine(HttpMethod method, Uri uri, HttpProtocol httpProtocol) {
        this.method = method;
        this.uri = uri;
        this.httpProtocol = httpProtocol;
    }

    public static RequestLine from(String fullRequestLine) {
        if (StringUtils.isEmpty(fullRequestLine)) {
            throw new IllegalArgumentException(REQUEST_LINE_IS_INVALID);
        }

        String[] values = fullRequestLine.split(SPACE_DELIMITER);
        if (values.length != 3) {
            throw new IllegalArgumentException(REQUEST_LINE_IS_INVALID);
        }

        return new RequestLine(HttpMethod.valueOf(values[0]), Uri.from(values[1]), HttpProtocol.from(values[2]));
    }

    public static RequestLine of(String method, String uri, String protocol, String version) {
        return new RequestLine(HttpMethod.valueOf(method), Uri.from(uri), HttpProtocol.of(protocol, version));
    }

    public boolean hasBody() {
        return this.method.isPost();
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

    public Uri getUri() {
        return uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return method == that.method &&
                Objects.equals(uri, that.uri) &&
                httpProtocol == that.httpProtocol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, uri, httpProtocol);
    }
}
