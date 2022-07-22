package http.httprequest.requestline;

import java.util.Objects;

public class Path {
    public static final String QUERY_STRING_DELIMITER = "?";
    public static final String EMPTY = "";
    private final String path;

    private final Params params;

    public Path(String path,
                Params params) {
        this.path = path;
        this.params = params;
    }

    public static Path from(String pathWithQueryString) {
        String path = getPath(pathWithQueryString);
        Params params = Params.from(findQueryString(pathWithQueryString));
        return new Path(path ,params);
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
        return path.equals(path1.path) && params.equals(path1.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, params);
    }
}

