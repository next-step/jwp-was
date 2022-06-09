package webserver;

import exception.IllegalQueryStringException;
import exception.IllegalQueryStringKeyException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class QueryString {
    private final Map<String, String> queryString;

    public QueryString() {
        this.queryString = Collections.emptyMap();
    }

    private QueryString(Map<String, String> queryString) {
        this.queryString = queryString;
    }

    public String get(String key) {
        return Optional.ofNullable(queryString.get(key))
                .orElseThrow(() -> new IllegalQueryStringKeyException(key));
    }

    public static QueryString from(String queryString) {
        String delimiter = "&";
        if (!queryString.contains(delimiter)) {
            return new QueryString();
        }
        String[] queries = queryString.split(delimiter);
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
                .collect(Collectors.toUnmodifiableMap(
                        tuple -> tuple[0],
                        tuple -> tuple[1]
                ));
    }
}
