package http;

import utils.Token;

public class Path {
    private static final String PATH_AND_QUERY_STRING_DELIMITER = "\\?";
    private static final int MINIMUM_REQUIRED_TOKEN_SIZE = 1;

    private final String path;
    private final QueryString queryString;

    public Path(final String pathStr) {
        Token token = Token.init(pathStr, PATH_AND_QUERY_STRING_DELIMITER);
        token.validate(MINIMUM_REQUIRED_TOKEN_SIZE);

        this.path = token.nextToken();
        this.queryString = new QueryString(token.nextToken());
    }

    public String getPath() {
        return path;
    }

    public String getParameter(final String parameterName) {
        return queryString.get(parameterName);
    }
}
