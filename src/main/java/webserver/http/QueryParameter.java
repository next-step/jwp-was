package webserver.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryParameter {

    private static final String QUERY_STRING_DELIMITER = "\\&";
    private static final String KEY_VALUE_DELIMITER = "\\=";
    private static final int QUERY_STRING_PAIR_COUNT = 2;

    private Map<String, String> queryParameters;

    private QueryParameter(Map<String, String> queryParameters) {
        this.queryParameters = new HashMap<>();
        this.queryParameters = queryParameters;
    }

    public static QueryParameter parse(String queryStringValue) {
        if (queryStringValue.isEmpty())
            return new QueryParameter(Collections.emptyMap());

        String [] keyValues = queryStringValue.split(QUERY_STRING_DELIMITER);

        return new QueryParameter(generateQueryParameters(keyValues));
    }

    private static Map<String, String> generateQueryParameters(String[] keyValues) {
        Map<String, String> parameters = Arrays.stream(keyValues)
                .map(keyValue -> keyValue.split(KEY_VALUE_DELIMITER))
                .filter(QueryParameter::hasKeyValue)
                .collect(Collectors.toMap(values -> values[0], values -> values[1], (a, b) -> b));

        return parameters;
    }

    private static boolean hasKeyValue(String[] values) {
        return values.length == QUERY_STRING_PAIR_COUNT;
    }

    public Map<String, String> getQueryParameters() {
        return Collections.unmodifiableMap(queryParameters);
    }
}
