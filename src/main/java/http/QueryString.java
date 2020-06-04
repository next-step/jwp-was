package http;

import utils.StringUtil;
import utils.Token;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QueryString {
    private static final String PARAMETER_DELIMITER = "&";

    private static final Map<String, String> parameters = new HashMap<>();

    public QueryString(final String queryString) {
        if (StringUtil.isEmpty(queryString)) {
            return;
        }

        Token token = Token.init(queryString, PARAMETER_DELIMITER);

        token.getAllTokens();

    }

    private void initQueryString(final String queryString) {
        String[] queries = queryString.split(PARAMETER_DELIMITER);

        Arrays.stream(queries)
                .forEach(this::parseQuery);
    }

    private void parseQuery(final String query) {
        String[] tokens = query.split("=");
        String key = tokens[0];
        String value = tokens[1];

        parameters.put(key, value);
    }

    public String get(final String parameter) {
        return parameters.getOrDefault(parameter, null);
    }
}
