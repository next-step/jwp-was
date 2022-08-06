package webserver;

public class Path {

    private static final String QUERY_STRING_DELIMITER = "\\?";
    private static final int NUMBER_OF_QUERY_STRING = 2;

    private String path;
    private QueryString queryString;

    public Path(String path) {
        this.path = path;
    }

    public Path(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static Path parse(String notParsingPath) {
        String[] split = notParsingPath.split(QUERY_STRING_DELIMITER);

        if (!split[0].startsWith("/")) {
            throw new IllegalArgumentException("올바르지 않은 PATH 입니다.");
        }
        if (!hasQueryString(split)) {
            return new Path(
                    split[0]
            );
        }
        return new Path(
                split[0],
                QueryString.parse(split[1])
        );
    }

    public static boolean hasQueryString(String[] split) {
        return split.length == NUMBER_OF_QUERY_STRING;
    }

    public String getPath() {
        return path;
    }

    public QueryString getQueryString() {
        return queryString;
    }
}
