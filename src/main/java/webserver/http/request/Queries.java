package webserver.http.request;

import java.util.*;

public class Queries {
    public static final String QUERY_STRING_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";
    private final Map<String, String> queriesByKey = new HashMap<>();

    private Queries() {
    }

    public static Queries from(String queryString) {
        Queries queries = new Queries();

        if (Objects.isNull(queryString) || queryString.isEmpty()) {
            return queries;
        }

        Arrays.stream(queryString.split(QUERY_STRING_DELIMITER))
                .map(qs -> qs.split(KEY_VALUE_DELIMITER))
                .forEach(queries::put);

        return queries;
    }

    private void put(String[] keyValue) {
        queriesByKey.put(keyValue[0], keyValue[1]);
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(queriesByKey.get(key));
    }
}
