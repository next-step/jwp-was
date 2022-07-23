package webserver.http;

import java.util.Objects;

class RequestLine {

    private static final int REQUEST_LINE_LENGTH = 3;

    private static final String REQUEST_LINE_DELIMITER = " ";

    private static final int METHOD_IDX = 0;

    private static final int PATH_IDX = 1;

    private static final int PROTOCOL_IDX = 2;

    private final HttpMethod method;

    private final RequestURI requestUri;

    private final ProtocolVersion protocol;

    private RequestLine(HttpMethod method, RequestURI requestUri, ProtocolVersion protocol) {
        this.method = method;
        this.requestUri = requestUri;
        this.protocol = protocol;
    }

    public static RequestLine parseOf(String httpRequestLine) {
        String[] requestLineValues = httpRequestLine.split(REQUEST_LINE_DELIMITER);

        if (requestLineValues.length != REQUEST_LINE_LENGTH) {
            throw new IllegalArgumentException(String.format("[%s] 유효한 RequestLine 이 아님", httpRequestLine));
        }

        return new RequestLine(
                HttpMethod.valueOf(requestLineValues[METHOD_IDX]),
                new RequestURI(requestLineValues[PATH_IDX]),
                ProtocolVersion.parseOf(requestLineValues[PROTOCOL_IDX]));
    }

    HttpMethod getMethod() {
        return method;
    }

    ProtocolVersion getProtocolVersion() {
        return protocol;
    }

    RequestURI getPath() {
        return requestUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return method == that.method && Objects.equals(requestUri, that.requestUri) && Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, requestUri, protocol);
    }
}
