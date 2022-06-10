package webserver;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class QueryString {
    private final static String QUERY_STRING_DELIMITER = "&";
    private final static String KEY_VALUE_DELIMITER = "=";

    private final Map<String, String> queryString;

    public QueryString() {
        this.queryString = Collections.emptyMap();
    }

    private QueryString(Map<String, String> input) {
        this.queryString = input;
    }

    public static QueryString from(String input) {
        if (Objects.isNull(input)) {
            return new QueryString();
        }

        try {
            Map<String, String> collect = Arrays.stream(input.split(QUERY_STRING_DELIMITER))
                    .map(query -> query.split(KEY_VALUE_DELIMITER))
                    .collect(Collectors.toUnmodifiableMap(
                            keyValue -> keyValue[0],
                            keyValue -> keyValue[1]
                    ));

            return new QueryString(collect);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String get(String key) {
        return Optional.ofNullable(queryString.get(key))
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryString that = (QueryString) o;
        return Objects.equals(queryString, that.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(QUERY_STRING_DELIMITER, KEY_VALUE_DELIMITER, queryString);
    }
}
