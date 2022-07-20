package webserver;

import java.util.Objects;

public class RequestPath {

    private static final int LENGTH_CONTAINING_QUERY_STR = 2;

    private static final int QUERY_STRING_IDX = 1;

    private static final String FIRST_CHAR_OF_PATH = "/";

    private static final String QUERY_STR_DELIMITER = "\\?";

    private final String path;

    RequestPath(String path) {
        validatePath(path);
        this.path = path;
    }
    QueryString parseQueryString() {
        return QueryString.parseOf(getQueryString());
    }

    private void validatePath(String path) {
        if (path != null && !path.startsWith(FIRST_CHAR_OF_PATH)) {
            throw new IllegalArgumentException(String.format("[%s] 유효한 RequestPath 가 아님", path));
        }
    }

    private String getQueryString() {
        String[] splitPath = path.split(QUERY_STR_DELIMITER);

        if (splitPath.length < LENGTH_CONTAINING_QUERY_STR) {
            return "";
        }

        return splitPath[QUERY_STRING_IDX];
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestPath that = (RequestPath) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
