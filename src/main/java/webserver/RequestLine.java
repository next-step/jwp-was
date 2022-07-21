package webserver;

import static exception.ExceptionStrings.INVALID_REQUEST_LINE;
import static exception.ExceptionStrings.INVALID_REQUEST_PATH;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import webserver.enums.HttpMethod;
import webserver.enums.RequestProtocol;

/**
 * HTTP Request 의 첫번째 라인 (== Start Line)
 */
public final class RequestLine {

    private static final int REQUEST_LINE_COMPONENT_COUNT = 3;
    private static final String REQUEST_LINE_SPLIT_REGEX = " ";

    private HttpMethod requestMethod;
    private String path;
    private RequestProtocol protocol;
    private Map<String, String> queryStringsMap;

    private RequestLine(HttpMethod requestMethod, PathAndQueryStrings pathAndQueryStrings, RequestProtocol protocol) {
        this.requestMethod = requestMethod;
        this.path = pathAndQueryStrings.path;
        this.protocol = protocol;
        this.queryStringsMap = pathAndQueryStrings.queryStringsMap;
    }

    public static RequestLine of(String startLine) {
        validate(startLine);

        String[] strings = startLine.split(REQUEST_LINE_SPLIT_REGEX);

        return RequestLine.of(HttpMethod.valueOf(strings[0]), strings[1], RequestProtocol.of(strings[2]));
    }

    public static RequestLine of(HttpMethod method, String path, RequestProtocol protocol) {
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

    public RequestProtocol getProtocol() {
        return protocol;
    }

    public Map<String, String> getQueryStringsMap() {
        return queryStringsMap;
    }

    static final class PathAndQueryStrings {

        private static final int PATH_COMPONENT_COUNT = 2;
        private static final String PATH_SPLIT_REGEX = "\\?";
        private static final String PATH_QUERY_SPLIT_REGEX = "&";
        private static final String QUERY_STRING_SPLIT_REGEX = "=";

        public String path;
        public Map<String, String> queryStringsMap;

        public PathAndQueryStrings(String path) {
            this(path, Collections.emptyMap());
        }

        public PathAndQueryStrings(String path, Map<String, String> queryStringsMap) {
            this.path = path;
            this.queryStringsMap = queryStringsMap;
        }

        public static PathAndQueryStrings of(String path) {
            PathAndQueryStrings.validate(path);

            String[] pathStrings = path.split(PATH_SPLIT_REGEX);
            if (pathStrings.length < PATH_COMPONENT_COUNT) {
                return new PathAndQueryStrings(pathStrings[0]);
            }

            return new PathAndQueryStrings(pathStrings[0], parseQueryStrings(pathStrings[1].split(PATH_QUERY_SPLIT_REGEX)));
        }

        private static void validate(String path) {
            Objects.requireNonNull(path, INVALID_REQUEST_PATH);

            if (path.isEmpty() || path.isBlank()) {
                throw new IllegalArgumentException(INVALID_REQUEST_PATH);
            }
        }

        private static Map<String, String> parseQueryStrings(String[] queryStrings) {
            Map<String, String> queryStringsMap = new HashMap<>();
            for (int i = 0; i < queryStrings.length; ++i) {
                String[] queryString = queryStrings[i].split(QUERY_STRING_SPLIT_REGEX);
                queryStringsMap.put(queryString[0], queryString[1]);
            }

            return queryStringsMap;
        }
    }

}
