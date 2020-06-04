package http;

import utils.Token;

import java.util.Arrays;
import java.util.HashMap;

public class Path {
    public static final String PATH_AND_QUERY_STRING_DELIMITER = "\\?";
    private final String path;
    private final HashMap<String, String> queryString;

    public Path(final String pathStr) {
        Token token = Token.init(pathStr, PATH_AND_QUERY_STRING_DELIMITER);
        token.validate(1);

        this.path = token.nextToken();
        this.queryString = new HashMap<>();
        token.nextToken();
    }

    private void initQueryString(final String queryString) {
        String[] queries = queryString.split("&");

        Arrays.stream(queries)
                .forEach(this::parseQuery);
    }

    private void parseQuery(final String query) {
        String[] tokens = query.split("=");
        String key = tokens[0];
        String value = tokens[1];

        queryString.put(key, value);
    }

    public String getPath() {
        return path;
    }

    public String getQueryString(final String parameterName) {
        return queryString.getOrDefault(parameterName, null);
    }
}
