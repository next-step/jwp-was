package webserver.request;

import org.springframework.util.StringUtils;

public class Path {

    private static final String EMPTY_QUERY_STRING = "";
    private static final String QUERY_STRING_DELIMITER = "\\?";
    private static final int INDEX_OF_LOCATION = 0;
    private static final int INDEX_OF_QUERY_STRING = 1;
    private static final int VALID_LENGTH_OF_QUERY_STRING_TOKENS = 2;

    private final String location;
    private final String queryString;

    private Path(final String location) {
        this(location, EMPTY_QUERY_STRING);
    }

    private Path(final String location, final String queryString) {
        this.location = location;
        this.queryString = queryString;
    }

    public static Path from(final String path) {
        if (!StringUtils.hasText(path)) {
            throw new IllegalArgumentException("빈 문자열은 파싱할 수 없습니다.");
        }

        final String[] tokens = path.split(QUERY_STRING_DELIMITER);
        if (hasQueryString(tokens)) {
            return new Path(tokens[INDEX_OF_LOCATION], tokens[INDEX_OF_QUERY_STRING]);
        }

        return new Path(tokens[INDEX_OF_LOCATION]);
    }

    private static boolean hasQueryString(final String[] tokens) {
        return tokens.length == VALID_LENGTH_OF_QUERY_STRING_TOKENS;
    }

    public String getLocation() {
        return location;
    }

    public String getQueryString() {
        return queryString;
    }
}
