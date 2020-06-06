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

    public QueryString(final String queryString) {
        if (StringUtil.isEmpty(queryString)) {
            return;
        }

        Tokens tokens = Tokens.init(queryString, PARAMETER_DELIMITER);
        tokens.getAllTokens()
                .forEach(this::parseQuery);
    }

    private void parseQuery(final String query) {
        Tokens tokens = Tokens.init(query, KEY_AND_VALUE_DELIMITER);
        tokens.validate(KEY_AND_VALUE_TOKEN_SIZE);

        parameters.put(tokens.nextToken(), tokens.nextToken());
    }

    public String get(final String parameter) {
        return parameters.getOrDefault(parameter, null);
    }
}
