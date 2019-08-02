package webserver.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestParameter {

    public static final RequestParameter EMPTY = new RequestParameter(Collections.emptyMap());
    private static final String QUERY_DELIMITER = "&";
    private static final String QUERY_KEY_VALUE_DELIMITER = "=";
    private static final String EMPTY_PARAMETER = "";

    private Map<String, String> parameters;

    private RequestParameter(Map<String, String> parameters) {
        this.parameters = Collections.unmodifiableMap(parameters);
    }

    public static RequestParameter parse(String queryString) {
        if (queryString == null || queryString.isEmpty()) {
            return EMPTY;
        }

        Map<String, String> params = new HashMap<>();
        for (String param : queryString.split(QUERY_DELIMITER)) {
            String[] entry = param.split(QUERY_KEY_VALUE_DELIMITER);
            params.put(entry[0], getParamValueOrDefault(entry));
        }
        return new RequestParameter(params);
    }

    private static String getParamValueOrDefault(String[] entry) {
        if (entry.length <= 1) {
            return EMPTY_PARAMETER;
        }
        return entry[1];
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}