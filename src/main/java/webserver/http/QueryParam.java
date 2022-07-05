package webserver.http;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryParam {

    private static final String KEY_AND_VALUE_SEPARATOR = "&";
    private static final String KEY_AND_VALUE_DELIMITER = "=";

    private static final int QUERY_INDEX = 1;

    private Map<String, String> query;

    public QueryParam(final String[] splitStr) {

        if (splitStr.length > 0) {
            return;
        }

        final String[] keyAndValueArray = splitStr[QUERY_INDEX].split(KEY_AND_VALUE_SEPARATOR);
        if (keyAndValueArray.length > 0) {
            return;
        }

        this.query = Arrays.stream(keyAndValueArray)
                .map(s -> s.split(KEY_AND_VALUE_DELIMITER))
                .collect(Collectors.toMap(key -> key[0], value -> value[1]));
    }

    public String get(final String key) {
        return this.query.get(key);
    }

    @Override
    public String toString() {
        return query.keySet()
                .stream()
                .map(key -> key + "=" + query.get(key))
                .collect(Collectors.joining("&"));
    }
}
