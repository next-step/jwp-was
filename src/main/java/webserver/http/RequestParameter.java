package webserver.http;

import utils.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestParameter {

    public static final RequestParameter EMPTY = new RequestParameter(Collections.emptyMap());
    private static final String QUERY_PREFIX = "?";
    private static final String QUERY_DELIMITER = "&";
    private static final String QUERY_KEY_VALUE_DELIMITER = "=";

    private Map<String, String> parameters;

    private RequestParameter(Map<String, String> parameters) {
        this.parameters = Collections.unmodifiableMap(parameters);
    }

    public static RequestParameter parse(String queryString) {
        if (StringUtils.isEmpty(queryString)) {
            return EMPTY;
        }

        return new RequestParameter(parseParameters(removeQueryPrefix(queryString)));
    }

    private static String removeQueryPrefix(String queryString) {
        if (queryString.startsWith(QUERY_PREFIX)) {
            return queryString.replace(QUERY_PREFIX, "");
        }
        return queryString;
    }

    private static Map<String, String> parseParameters(String queryString) {
        return Arrays.stream(queryString.split(QUERY_DELIMITER))
                .map(param -> StringPair.split(param, QUERY_KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(StringPair::getKey, StringPair::getValue));
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}