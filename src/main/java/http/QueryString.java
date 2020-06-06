package http;

import utils.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class QueryString {
    private static final String PARAMETER_DELIMITER = "&";
    private static final String KEY_AND_VALUE_DELIMITER = "=";
    private static final int KEY_AND_VALUE_TOKEN_SIZE = 2;

    private static final Map<String, String> parameters = new HashMap<>();

    private QueryString() {}

    public static QueryString init() {
        return new QueryString();
    }

    public static QueryString init(final String query) {
        QueryString queryStr = new QueryString();
        queryStr.update(query);

        return queryStr;
    }

    public void update(final String queryString) {
        if (StringUtil.isEmpty(queryString)) {
            return;
        }

        StringTokenizer tokens = new StringTokenizer(queryString, PARAMETER_DELIMITER);

        while (tokens.hasMoreTokens()) {
            parseQuery(tokens.nextToken());
        }
    }

    private void parseQuery(final String query) {
        String[] tokens = query.split(KEY_AND_VALUE_DELIMITER);

        if (tokens.length != KEY_AND_VALUE_TOKEN_SIZE) {
            throw new IllegalArgumentException("Invalid query string : " + query);
        }

        parameters.put(tokens[0], tokens[1]);
    }

    public String get(final String parameter) {
        return parameters.get(parameter);
    }

    public Map<String, String> getAll() {
        return parameters;
    }
}
