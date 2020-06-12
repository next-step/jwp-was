package http.request.querystring;

import http.request.querystring.exception.QueryStringParsingException;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class QueryString {

    private static final String QUERY_DELIMITER = "&";
    private static final String PARAMETER_DELIMITER = "=";
    private static final int PARAMETER_TOKEN_SIZE = 2;

    private final Map<String, String> queryParameters = new HashMap<>();

    public QueryString(String query) {
        if (query == null) {
            throw new QueryStringParsingException("Parameter for creating QueryString is null.");
        }

        String[] parameters = query.trim().split(QUERY_DELIMITER);
        for (String parameter : parameters) {
            registerQueryParameter(parameter);
        }
    }

    public String get(String key) {
        return queryParameters.get(key);
    }

    public int size() {
        return queryParameters.size();
    }

    private void registerQueryParameter(String parameter) {
        if (StringUtils.isEmpty(parameter)) {
            return;
        }

        String[] parameterTokens = parameter.split(PARAMETER_DELIMITER);
        if (parameterTokens.length > PARAMETER_TOKEN_SIZE) {
            throw new QueryStringParsingException("QueryString is Illegal Format.");
        }

        String value = (parameterTokens.length == PARAMETER_TOKEN_SIZE) ? parameterTokens[1] : "";
        queryParameters.put(parameterTokens[0], value);
    }
}
