package webserver;

public class Path {

    private static final int PATH_INDEX = 0;
    private static final String QUERY_STRING_START_CHARACTER = "?";
    private static final String REQUEST_URL_PATH_DELIMITER = "\\?";

    private String path;

    protected Path() {

    }

    private Path(final String path) {
        this.path = path;
    }

    public static Path from(final String requestUrl) {
        if (requestUrl.contains(QUERY_STRING_START_CHARACTER)) {
            return new Path(requestUrl.split(REQUEST_URL_PATH_DELIMITER)[PATH_INDEX]);
        }

        return new Path(requestUrl);
    }

    public String getPath() {
        return this.path;
    }
}
