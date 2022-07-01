package webserver.common;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import webserver.common.exception.IllegalQueryStringException;
import webserver.common.exception.IllegalQueryStringKeyException;

public class QueryString {
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
        if (!queryString.contains("=")) {
            return new QueryString();
        }
        String[] queries = queryString.split("&");
        try {
            return new QueryString(parseQueries(queries));
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw new IllegalQueryStringException(queryString);
        }
    }

    private static Map<String, String> parseQueries(String[] queries) {
        String delimiter = "=";
        return Arrays.stream(queries)
                .map(query -> query.split(delimiter))
                .collect(Collectors.toMap(
                        tuple -> tuple[0],
                        tuple -> tuple[1]
                ));
    }

    @Override
    public String toString() {
        return queryString.entrySet().stream()
                .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("&"));
    }
}
