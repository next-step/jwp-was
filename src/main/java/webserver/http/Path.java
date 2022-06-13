package webserver.http;

import org.apache.logging.log4j.util.Strings;

public class Path {

    private static final String DELIMITER = "\\?";
    private static final int INDEX_OF_PATH = 0;
    private static final int INDEX_OF_QUERY_STRING = 1;
    private static final int LENGTH_OF_QUERY_STRING = 2;

    private final String path;
    private final QueryString queryString;

    public Path(final String path) {
        validatePath(path);
        final var splitPath = path.split(DELIMITER);
        this.path = splitPath[INDEX_OF_PATH];
        this.queryString = new QueryString(defaultQueryStringIfNotExists(splitPath));
    }

    private String defaultQueryStringIfNotExists(final String[] splitPath) {
        if (splitPath.length == LENGTH_OF_QUERY_STRING) {
            return splitPath[INDEX_OF_QUERY_STRING];
        }
        return "";
    }

    private void validatePath(final String path) {
        if (Strings.isBlank(path)) {
            throw new IllegalArgumentException("Path는 필수입니다.");
        }
    }

    public String getPath() {
        return path;
    }
}
