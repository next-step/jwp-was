package http;

import java.util.HashMap;
import java.util.Map;

public class QueryString {
    private static final int MIN_QUERY_VALUE_LENGTH = 2;
    private static final String DELIMITER_OF_PATH_PARAMS = "&";
    private static final String DELIMITER_OF_PATH_PARMAM = "=";

    private final Map<String, String> values;

    private QueryString(final Map<String, String> values) {
        this.values = values;
    }

    public static QueryString of(final String queryString) {
        final Map<String, String> pathParamMap = new HashMap<>();
        if (queryString == null || queryString.isEmpty()) {
            return new QueryString(pathParamMap);
        }

        final String[] params = queryString.split(DELIMITER_OF_PATH_PARAMS);
        for (String param : params) {
            final String[] keyAndValue = param.split(DELIMITER_OF_PATH_PARMAM);
            validateQueryStringSize(keyAndValue);
            pathParamMap.put(keyAndValue[0], keyAndValue[1]);
        }
        return new QueryString(pathParamMap);
    }

    private static void validateQueryStringSize(final String[] keyAndValue) {
        if (keyAndValue.length < MIN_QUERY_VALUE_LENGTH) {
            throw new IllegalArgumentException("QueryString must have key and value");
        }
    }

    public String getParameter(final String key) {
        return values.getOrDefault(key, null);
    }

}
