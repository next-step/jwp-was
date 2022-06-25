package webserver.http.request;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toMap;

public class RequestParameters {
    private final Map<String, String> queryStrings;

    public RequestParameters(Map<String, String> queryStrings) {
        this.queryStrings = queryStrings;
    }

    public static RequestParameters of(String value) {
        if (Objects.isNull(value)) {
            return null;
        }

        Map<String, String> collect = Arrays.stream(value.split("&"))
                                            .map(token -> token.split("="))
                                            .collect(toMap(strings -> strings[0], strings -> strings[1]));

        return new RequestParameters(collect);
    }

    public String get(String key) {
        return queryStrings.get(key);
    }
}
