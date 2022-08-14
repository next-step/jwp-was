package http.httprequest.requestline;

import java.util.Objects;

public class Path {
    public static final String QUERY_STRING_DELIMITER = "?";
    public static final String EMPTY = "";
    private final String path;
    private final RequestParams requestParams;

    public Path(String path, RequestParams requestParams) {
        this.path = path;
        this.requestParams = requestParams;
    }

    public String getPath() {
        return path;
    }

    public static Path from(String pathWithQueryString) {
        String path = getPath(pathWithQueryString);
        RequestParams requestParams = RequestParams.from(findQueryString(pathWithQueryString));
        return new Path(path , requestParams);
    }

    private static String getPath(String pathWithQueryString) {
        if (!pathWithQueryString.contains(QUERY_STRING_DELIMITER)) {
            return pathWithQueryString;
        }

        return pathWithQueryString.substring(0, pathWithQueryString.indexOf(QUERY_STRING_DELIMITER));
    }

    private static String findQueryString(String pathWithQueryString) {
        if (!pathWithQueryString.contains(QUERY_STRING_DELIMITER)) {
            return EMPTY;
        }

        return pathWithQueryString.substring(pathWithQueryString.indexOf(QUERY_STRING_DELIMITER) + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path1 = (Path) o;
        return path.equals(path1.path) && requestParams.equals(path1.requestParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, requestParams);
    }
}

