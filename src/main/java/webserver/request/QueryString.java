package webserver.request;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import webserver.request.exception.IllegalQueryStringException;
import webserver.request.exception.IllegalQueryStringKeyException;

public class QueryString {
    private static final String MAP_DELIMITER = "=";
    private static final String QUERY_DELIMITER = "&";
    private final Map<String, String> queryString;

    public QueryString() {
        this.queryString = new LinkedHashMap<>();
    }

    private QueryString(Map<String, String> queryString) {
        this.queryString = queryString;
    }

    public String get(String key) {
        return Optional.ofNullable(queryString.get(key))
                .orElseThrow(() -> new IllegalQueryStringKeyException(key));
    }

    public QueryString put(String key, String value) {
        queryString.put(key, value);
        return this;
    }

    public static QueryString from(String queryString) {
        if (!queryString.contains(MAP_DELIMITER)) {
            return new QueryString();
        }
        String[] queries = queryString.split(QUERY_DELIMITER);
        try {
            return new QueryString(parse(queries));
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw new IllegalQueryStringException(queryString);
        }
    }

    private static Map<String, String> parse(String[] queries) {
        return Arrays.stream(queries)
                .map(query -> query.split(MAP_DELIMITER))
                .collect(Collectors.toMap(
                        tuple -> tuple[0],
                        tuple -> tuple[1]
                ));
    }

    @Override
    public String toString() {
        return queryString.entrySet().stream()
                .map(entry -> String.format("%s%s%s", entry.getKey(), MAP_DELIMITER, entry.getValue()))
                .collect(Collectors.joining(QUERY_DELIMITER));
    }
}
