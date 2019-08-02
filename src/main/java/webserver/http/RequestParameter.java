package webserver.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

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

        return new RequestParameter(parseParameters(queryString));
    }

    private static Map<String, String> parseParameters(String queryString) {
        return Arrays.stream(queryString.split(QUERY_DELIMITER))
                .map(param -> param.split(QUERY_KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(
                        param -> param[0],
                        RequestParameter::getParamValueOrDefault
                ));
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