package webserver.utils;

import static exception.ExceptionStrings.INVALID_REQUEST_PATH;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class PathAndQueryStrings {
    private static final int PATH_COMPONENT_COUNT = 2;
    private static final String PATH_SPLIT_REGEX = "\\?";
    private static final String PATH_QUERY_SPLIT_REGEX = "&";

    private String path;
    private Map<String, String> queryStringsMap;

    public PathAndQueryStrings(String path) {
        this(path, Collections.emptyMap());
    }

    public PathAndQueryStrings(String path, Map<String, String> queryStringsMap) {
        this.path = path;
        this.queryStringsMap = queryStringsMap;
    }

    public PathAndQueryStrings(String path, QueryStrings queryStrings) {
        this.path = path;
        this.queryStringsMap = queryStrings.map();
    }

    public static PathAndQueryStrings of(String path) {
        PathAndQueryStrings.validate(path);

        String[] pathStrings = path.split(PATH_SPLIT_REGEX);
        if (pathStrings.length < PATH_COMPONENT_COUNT) {
            return new PathAndQueryStrings(pathStrings[0]);
        }

        return new PathAndQueryStrings(pathStrings[0], QueryStrings.of(pathStrings[1]));
    }

    private static void validate(String path) {
        Objects.requireNonNull(path, INVALID_REQUEST_PATH);

        if (path.isEmpty() || path.isBlank()) {
            throw new IllegalArgumentException(INVALID_REQUEST_PATH);
        }
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryStringsMap() {
        return queryStringsMap;
    }
}
