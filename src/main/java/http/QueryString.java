package http;

import utils.StringUtil;
import utils.Tokens;

import java.util.HashMap;
import java.util.Map;

public class QueryString {
    private static final String PARAMETER_DELIMITER = "&";
    private static final String KEY_AND_VALUE_DELIMITER = "=";
    private static final int KEY_AND_VALUE_TOKEN_SIZE = 2;

    private static final Map<String, String> parameters = new HashMap<>();

    private QueryString() {}

    public static QueryString init() {
        return new QueryString();
    }

    public void update(final String queryString) {
        if (StringUtil.isEmpty(queryString)) {
            return;
        }

        Tokens tokens = Tokens.init(queryString, PARAMETER_DELIMITER);
        tokens.getAllTokens()
                .forEach(this::parseQuery);
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
}
