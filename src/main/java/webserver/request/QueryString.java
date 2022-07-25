package webserver.request;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryString {
    private static final String PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    private final Map<String, String> params;

    public QueryString(String queryString) {
        this.params = Stream.of(queryString.split(PARAM_DELIMITER))
                .map(param -> param.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(value -> value[0], value -> value[1]));
    }

    public Map<String, String> getParams() {
        return this.params;
    }
}
