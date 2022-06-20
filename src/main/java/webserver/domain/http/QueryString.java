package webserver.domain.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class QueryString {

    private final static String QUERY_STRING_DELIMITER = "&";
    private final static String KEY_VALUE_DELIMITER = "=";

    private final Map<String, String> parameter;

    public QueryString() {
        this.parameter = Collections.emptyMap();
    }

    private QueryString(Map<String, String> input) {
        this.parameter = input;
    }

    public static QueryString from(String input) {
        if (Objects.isNull(input) || input.isEmpty()) {
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
        return Optional.ofNullable(parameter.get(key))
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryString that = (QueryString) o;
        return Objects.equals(parameter, that.parameter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameter);
    }

    @Override
    public String toString() {
        return "HttpParameter{" +
                "parameter=" + parameter +
                '}';
    }
}
