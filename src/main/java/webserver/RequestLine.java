package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import webserver.enums.HttpMethod;
import webserver.enums.RequestProtocol;

public final class RequestLine {

    private HttpMethod requestMethod;
    private String path;
    private RequestProtocol protocol;
    private String version;
    private Map<String, String> queryStringsMap;

    private RequestLine(HttpMethod requestMethod, PathAndQueryStrings pathAndQueryStrings, RequestProtocol protocol, String version) {
        this.requestMethod = requestMethod;
        this.path = pathAndQueryStrings.path;
        this.protocol = protocol;
        this.version = version;
        this.queryStringsMap = pathAndQueryStrings.queryStringsMap;
    }

    public static RequestLine of(String firstLine) {
        validate(firstLine);

        String[] strings = firstLine.split(" ");
        String[] protocolAndVersion = strings[2].split("/");

        if (protocolAndVersion.length < 2) {
            throw new IllegalArgumentException("Invalid protocol and version");
        }

        return RequestLine.of(HttpMethod.valueOf(strings[0]), strings[1], RequestProtocol.valueOf(protocolAndVersion[0]), protocolAndVersion[1]);
    }

    private static void validate(String line) {
        if (line == null || line.isEmpty() || line.isBlank()) {
            throw new IllegalArgumentException("Invalid requestLine string");
        }

        if (line.split(" ").length < 3) {
            throw new IllegalArgumentException("Invalid requestLine string");
        }
    }

    public static RequestLine of(HttpMethod method, String path, RequestProtocol protocol, String version) {
        return new RequestLine(method, PathAndQueryStrings.of(path), protocol, version);
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
        return requestMethod == that.requestMethod && Objects.equals(path, that.path) && protocol == that.protocol && Objects.equals(version, that.version) && Objects.equals(
            queryStringsMap, that.queryStringsMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestMethod, path, protocol, version, queryStringsMap);
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

            String[] queryStrings = pathStrings[1].split("&");
            Map<String, String> queryStringsMap = new HashMap<>();
            for (int i = 0; i < queryStrings.length; ++i) {
                String[] queryString = queryStrings[i].split("=");
                queryStringsMap.put(queryString[0], queryString[1]);
            }
            return new PathAndQueryStrings(pathStrings[0], queryStringsMap);
        }
    }

}
