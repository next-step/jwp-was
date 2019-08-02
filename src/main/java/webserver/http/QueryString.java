package webserver.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryString {

    private static final String QUERY_STRING_DELIMITER = "\\&";
    private static final String KEY_VALUE_DELIMITER = "\\=";
    private static final int KEY_VALUE_COUNT = 2;

    private Map<String, String> queryParameters;

    private QueryString(Map<String, String> queryParameters) {
        this.queryParameters = new HashMap<>();
        this.queryParameters = queryParameters;
    }

    public static QueryString parse(String queryStringValue) {
        if (queryStringValue.isEmpty())
            return new QueryString(Collections.emptyMap());

        Map<String, String> parameters;

        String [] keyValues = queryStringValue.split(QUERY_STRING_DELIMITER);

        parameters = Arrays.stream(keyValues)
                .map(keyValue -> keyValue.split(KEY_VALUE_DELIMITER))
                .filter(QueryString::hasKeyValue)
                .collect(Collectors.toMap(values -> values[0], values -> values[1], (a, b) -> b));

        return new QueryString(parameters);
    }

    private static boolean hasKeyValue(String[] values) {
        return values.length == KEY_VALUE_COUNT;
    }

    public Map<String, String> getQueryParameters() {
        return Collections.unmodifiableMap(queryParameters);
    }
}
