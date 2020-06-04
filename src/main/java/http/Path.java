package http;

import utils.Tokens;

public class Path {
    private static final String PATH_AND_QUERY_STRING_DELIMITER = "\\?";
    private static final int MINIMUM_REQUIRED_TOKEN_SIZE = 1;
    public static final char DOT = '.';

    private final String path;
    private final QueryString queryString;

    public Path(final String pathStr) {
        Tokens tokens = Tokens.init(pathStr, PATH_AND_QUERY_STRING_DELIMITER);
        tokens.validate(MINIMUM_REQUIRED_TOKEN_SIZE);

        this.path = tokens.nextToken();
        this.queryString = new QueryString(tokens.nextToken());
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
