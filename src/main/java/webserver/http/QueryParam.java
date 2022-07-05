package webserver.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryParam {

    private static final String KEY_AND_VALUE_SEPARATOR = "&";
    private static final String KEY_AND_VALUE_DELIMITER = "=";

    private static final int MIN_INDEX = 1;
    private static final int QUERY_INDEX = 1;

    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private Map<String, String> query = Collections.emptyMap();

    public QueryParam(final String[] splitStr) {

        if (splitStr.length <= MIN_INDEX) {
            return;
        }

        final String[] keyAndValueArray = splitStr[QUERY_INDEX].split(KEY_AND_VALUE_SEPARATOR);
        if (keyAndValueArray.length <= MIN_INDEX) {
            return;
        }

        this.query = Arrays.stream(keyAndValueArray)
                .map(s -> s.split(KEY_AND_VALUE_DELIMITER))
                .collect(Collectors.toMap(key -> key[KEY_INDEX], value -> value[VALUE_INDEX]));
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
