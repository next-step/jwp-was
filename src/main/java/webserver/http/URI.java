package webserver.http;

public class URI {

    private static final String QUERY_STRING_SEPARATOR = "\\?";

    private static final int PATH_INDEX = 0;

    private final String path;
    private final QueryParam queryParam;

    public URI(final String str) {

        final String[] splitStr = str.split(QUERY_STRING_SEPARATOR);

        this.path = splitStr[PATH_INDEX];
        this.queryParam = new QueryParam(splitStr);
    }

    public String get(final String key) {
        return this.queryParam.get(key);
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return String.join(QUERY_STRING_SEPARATOR, path, queryParam.toString());
    }
}
