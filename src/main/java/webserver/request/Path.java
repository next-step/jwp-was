package webserver.request;

import java.util.Objects;

public class Path {

    private static final String EMPTY_QUERY_STRING = "";
    private static final String QUERY_STRING_DELIMITER = "\\?";
    private static final int INDEX_OF_LOCATION = 0;
    private static final int INDEX_OF_QUERY_STRING = 1;
    private static final int VALID_LENGTH_OF_QUERY_STRING_TOKENS = 2;

    private final String location;
    private final String queryString;

    private Path(final String location, final String queryString) {
        this.location = location;
        this.queryString = queryString;
    }

    public static Path parse(final String path) {
        validate(path);

        final String[] tokens = path.split(QUERY_STRING_DELIMITER);
        if (hasQueryString(tokens)) {
            return of(tokens[INDEX_OF_LOCATION], tokens[INDEX_OF_QUERY_STRING]);
        }

        return from(tokens[INDEX_OF_LOCATION]);
    }

    private static void validate(final String path) {
        if (isEmpty(path)) {
            throw new IllegalArgumentException("빈 문자열은 파싱할 수 없습니다.");
        }
    }

    private static boolean isEmpty(final String path) {
        return path == null || path.isBlank();
    }

    public static Path from(final String location) {
        return of(location, EMPTY_QUERY_STRING);
    }

    public static Path of(final String location, final String queryString) {
        return new Path(location, queryString);
    }

    private static boolean hasQueryString(final String[] tokens) {
        return tokens.length == VALID_LENGTH_OF_QUERY_STRING_TOKENS;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Path path = (Path) o;
        return Objects.equals(location, path.location) && Objects.equals(queryString, path.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, queryString);
    }
}
