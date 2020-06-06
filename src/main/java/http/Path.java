package http;

public class Path {
    private static final String PATH_AND_QUERY_STRING_DELIMITER = "\\?";
    private static final char DOT = '.';

    private final String path;
    private final QueryString queryString = QueryString.init();

    public Path(final String pathStr) {
        String[] tokens = pathStr.split(PATH_AND_QUERY_STRING_DELIMITER);

        this.path = tokens[0];

        if (tokens.length > 1) {
            queryString.update(tokens[1]);
        }
    }

    public String getPath() {
        return path;
    }

    public String getExtension() {
        int lastIndexOfDot = path.lastIndexOf(DOT);

        if (lastIndexOfDot < 0) {
            return null;
        }

        return path.substring(lastIndexOfDot + 1);
    }

    public String getParameter(final String parameterName) {
        return queryString.get(parameterName);
    }
}
