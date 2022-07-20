package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import webserver.enums.HttpMethod;
import webserver.enums.RequestProtocol;

/**
 * HTTP Request 의 첫번째 라인 (== Start Line)
 */
public final class RequestLine {

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

        String[] strings = startLine.split(" ");

        return RequestLine.of(HttpMethod.valueOf(strings[0]), strings[1], RequestProtocol.of(strings[2]));
    }

    private static void validate(String line) {
        if (line == null || line.isEmpty() || line.isBlank()) {
            throw new IllegalArgumentException("Invalid requestLine string");
        }

        if (line.split(" ").length < 3) {
            throw new IllegalArgumentException("Invalid requestLine string");
        }
    }

    public static RequestLine of(HttpMethod method, String path, RequestProtocol protocol) {
        return new RequestLine(method, PathAndQueryStrings.of(path), protocol);
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

    public Optional<Map<String, String>> getQueryStringsMap() {
        return Optional.of(queryStringsMap);
    }

    static final class PathAndQueryStrings {

        public String path;
        public Map<String, String> queryStringsMap;

        public PathAndQueryStrings(String path) {
            this.path = path;
        }

        public PathAndQueryStrings(String path, Map<String, String> queryStringsMap) {
            this.path = path;
            this.queryStringsMap = queryStringsMap;
        }

        public static PathAndQueryStrings of(String path) {
            String[] pathStrings = path.split("\\?");
            if (pathStrings.length < 2) {
                return new PathAndQueryStrings(pathStrings[0]);
            }

            return new PathAndQueryStrings(pathStrings[0], parseQueryStrings(pathStrings[1].split("&")));
        }

        private static Map<String, String> parseQueryStrings(String[] queryStrings) {
            Map<String, String> queryStringsMap = new HashMap<>();
            for (int i = 0; i < queryStrings.length; ++i) {
                String[] queryString = queryStrings[i].split("=");
                queryStringsMap.put(queryString[0], queryString[1]);
            }

            return queryStringsMap;
        }
    }

}
