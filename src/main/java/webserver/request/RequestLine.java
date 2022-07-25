package webserver.request;

import static exception.ExceptionStrings.INVALID_REQUEST_LINE;

import java.util.Map;
import java.util.Objects;
import webserver.enums.HttpMethod;
import webserver.enums.Protocol;
import webserver.utils.PathAndQueryStrings;
import webserver.utils.QueryStrings;

/**
 * HTTP Request 의 첫번째 라인 (== Start Line)
 */
public final class RequestLine {

    private static final int REQUEST_LINE_COMPONENT_COUNT = 3;
    private static final String REQUEST_LINE_SPLIT_REGEX = " ";

    private HttpMethod requestMethod;
    private String path;
    private Protocol protocol;
    private Map<String, String> queryStringsMap;

    private RequestLine(HttpMethod requestMethod, PathAndQueryStrings queryStrings, Protocol protocol) {
        this.requestMethod = requestMethod;
        this.path = queryStrings.getPath();
        this.protocol = protocol;
        this.queryStringsMap = queryStrings.getQueryStringsMap();
    }

    public static RequestLine of(String startLine) {
        validate(startLine);

        String[] strings = startLine.split(REQUEST_LINE_SPLIT_REGEX);

        return RequestLine.of(HttpMethod.valueOf(strings[0]), strings[1], Protocol.of(strings[2]));
    }

    public static RequestLine of(HttpMethod method, String path, Protocol protocol) {
        return new RequestLine(method, PathAndQueryStrings.of(path), protocol);
    }

    private static void validate(String line) {
        Objects.requireNonNull(line, INVALID_REQUEST_LINE);

        if (line.isEmpty() || line.isBlank()) {
            throw new IllegalArgumentException(INVALID_REQUEST_LINE);
        }

        if (line.split(REQUEST_LINE_SPLIT_REGEX).length < REQUEST_LINE_COMPONENT_COUNT) {
            throw new IllegalArgumentException(INVALID_REQUEST_LINE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestLine that = (RequestLine) o;
        return requestMethod == that.requestMethod && Objects.equals(path, that.path) && protocol == that.protocol && Objects.equals(queryStringsMap, that.queryStringsMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestMethod, path, protocol, queryStringsMap);
    }

    public HttpMethod getHttpMethod() {
        return requestMethod;
    }

    public String getPath() {
        return path;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public Map<String, String> getQueryStringsMap() {
        return queryStringsMap;
    }

}
