package webserver.http;

import java.util.Map;
import java.util.Objects;

public class Path {

    private static final String QUERYSTRING_DELIMITER = "\\?";
    private static final String RESOURCE_DELIMITER = "\\.";

    private static final int INDEX_PATH_VALUE = 0;
    private static final int FIRST_INDEX = 0;
    private static final int INDEX_QUERY_STRING = 1;

    private final String value;
    private final QueryString queryString;

    private Path(String value) {
        this.value = value;
        this.queryString = null;
    }

    private Path(String value, String queryString) {
        this.value = value;
        this.queryString = new QueryString(queryString);
    }

    public static Path from(String requestPath) {
        final String[] pathElements = requestPath.split(QUERYSTRING_DELIMITER);
        if (pathElements.length == 1) {
            return new Path(pathElements[INDEX_PATH_VALUE]);
        }
        return new Path(pathElements[INDEX_PATH_VALUE], pathElements[INDEX_QUERY_STRING]);
    }

    public String getStaticResourceExtension() {
        String[] valueElements = value.split(RESOURCE_DELIMITER);
        return valueElements[valueElements.length - 1];
    }

    public String getValue() {
        return value;
    }

    public Map<String, String> getQueryStringMap() {
        if (queryString != null) {
            return queryString.getParameterMap();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path = (Path) o;
        return Objects.equals(value, path.value) && Objects.equals(queryString, path.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, queryString);
    }

    @Override
    public String toString() {
        return "Path{" +
                "value='" + value + '\'' +
                ", queryString=" + queryString +
                '}';
    }
}
