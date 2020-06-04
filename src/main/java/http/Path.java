package http;

import utils.Tokens;

public class Path {
    private static final String PATH_AND_QUERY_STRING_DELIMITER = "\\?";
    private static final int MINIMUM_REQUIRED_TOKEN_SIZE = 1;

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

    public String getParameter(final String parameterName) {
        return queryString.get(parameterName);
    }
}
