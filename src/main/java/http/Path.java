package http;

import java.util.Arrays;
import java.util.HashMap;

public class Path {
    private final String path;
    private final HashMap<String, String> queryString;

    public Path(final String pathStr) {
        if (pathStr == null) {
            throw new IllegalArgumentException("Path string is null");
        }

        String[] tokens = pathStr.split("\\?");

        this.path = tokens[0];
        this.queryString = new HashMap<>();

        if (tokens.length >= 2) {
            initQueryString(tokens[1]);
        }
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
