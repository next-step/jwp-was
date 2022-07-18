package http.request;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryParams {

    public static final QueryParams EMPTY = new QueryParams(Map.of());

    private static final String PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final Map<String, String> values;

    private QueryParams(Map<String, String> values) {
        this.values = values;
    }

    public static QueryParams of(String queryString) {
        var query = fetchQuery(queryString);

        var params = Arrays.stream(query.split(PARAM_DELIMITER))
            .collect(Collectors.toList());

        var values = params.stream()
            .map(it -> it.split(KEY_VALUE_DELIMITER))
            .map(it -> Map.entry(it[KEY_INDEX], it[VALUE_INDEX]))
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (p1, p2) -> p2));

        return new QueryParams(values);
    }

    private static String fetchQuery(String queryString) {
        if (queryString == null) {
            return "";
        }

        return queryString;
    }

    public String get(String key) {
        return values.get(key);
    }

    public Map<String, String> get() {
        return Map.copyOf(values);
    }
}
