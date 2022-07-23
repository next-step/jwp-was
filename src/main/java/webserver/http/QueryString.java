package webserver.http;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryString {

    private static final String ENTRY_DELIMITER = "&";

    private static final String KEY_VALUE_DELIMITER = "=";

    private static final int KEY_IDX = 0;

    private static final int VALUE_IDX = 1;

    private final Map<String, String> valuesByKey;

    private QueryString(Map<String, String> valuesByKey) {
        this.valuesByKey = Collections.unmodifiableMap(valuesByKey);
    }

    static QueryString parseOf(String queryString) {
        if (queryString == null || queryString.isEmpty()) {
            return new QueryString(Collections.emptyMap());
        }

        return new QueryString(
                Stream.of(queryString.split(ENTRY_DELIMITER))
                        .map(entries -> entries.split(KEY_VALUE_DELIMITER))
                        .collect(Collectors.toMap(entry -> entry[KEY_IDX], entry -> entry[VALUE_IDX]))
        );
    }

    String getValue(String key) {
        return valuesByKey.get(key);
    }
}
