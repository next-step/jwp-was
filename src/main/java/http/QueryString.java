package http;

import utils.StringUtil;
import utils.Token;

import java.util.HashMap;
import java.util.Map;

public class QueryString {
    private static final String PARAMETER_DELIMITER = "&";
    private static final String KEY_AND_VALUE_DELIMITER = "=";

    private static final Map<String, String> parameters = new HashMap<>();

    public QueryString(final String queryString) {
        if (StringUtil.isEmpty(queryString)) {
            return;
        }

        Token token = Token.init(queryString, PARAMETER_DELIMITER);
        token.getAllTokens()
                .forEach(this::parseQuery);
    }

    private void parseQuery(final String query) {
        Token token = Token.init(query, KEY_AND_VALUE_DELIMITER);
        token.validate(2);

        parameters.put(token.nextToken(), token.nextToken());
    }

    public String get(final String parameter) {
        return parameters.getOrDefault(parameter, null);
    }
}
