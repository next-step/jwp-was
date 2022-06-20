package webserver.http.request;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toMap;

public class QueryString {
    private final Map<String, String> queryStrings;

    public QueryString(Map<String, String> queryStrings) {
        this.queryStrings = queryStrings;
    }

    public static QueryString of(String values) {
        if (Objects.isNull(values)) {
            return null;
        }

        Map<String, String> collect = Arrays.stream(values.split("&"))
                                            .map(token -> token.split("="))
                                            .collect(toMap(strings -> strings[0], strings -> strings[1]));

        return new QueryString(collect);
    }

    public String get(String key) {
        return queryStrings.get(key);
    }
}
