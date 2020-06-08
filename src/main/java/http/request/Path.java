package http.request;

import utils.StringUtil;

public class Path {
    private static final String PATH_AND_QUERY_STRING_DELIMITER = "\\?";
    private static final char DOT = '.';

    private final String path;
    private final QueryString queryString;

    private Path(final String path, final String query) {
        this.path = path;
        this.queryString = new QueryString(query);
    }

    public static Path parse(final String path) {
        validate(path);
        String[] tokens = path.split(PATH_AND_QUERY_STRING_DELIMITER);

        String pathToken = tokens[0];
        String queryToken = "";

        if (tokens.length > 1) {
            queryToken = tokens[1];
        }

        return new Path(pathToken, queryToken);
    }

    private static void validate(String pathStr) {
        if (StringUtil.isEmpty(pathStr)) {
            throw new IllegalArgumentException("Path string is null");
        }
    }

    public String getPath() {
        return path;
    }

    public String getQueryString() {
        return queryString.getQueryString();
    }

    public String getExtension() {
        int lastIndexOfDot = path.lastIndexOf(DOT);

        if (lastIndexOfDot < 0) {
            return null;
        }

        return path.substring(lastIndexOfDot + 1);
    }

}
