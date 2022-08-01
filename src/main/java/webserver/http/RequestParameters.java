package webserver.http;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestParameters {

    private static final String ENTRY_DELIMITER = "&";

    private static final String KEY_VALUE_DELIMITER = "=";

    private static final int KEY_IDX = 0;

    private static final int VALUE_IDX = 1;

    private final Map<String, String> valuesByKey;

    private RequestParameters(Map<String, String> valuesByKey) {
        this.valuesByKey = Collections.unmodifiableMap(valuesByKey);
    }

    static RequestParameters parseOf(String queryString) {
        if (queryString == null || queryString.isEmpty()) {
            return new RequestParameters(Collections.emptyMap());
        }

        return new RequestParameters(
                Stream.of(queryString.split(ENTRY_DELIMITER))
                        .map(entries -> entries.split(KEY_VALUE_DELIMITER))
                        .collect(Collectors.toMap(entry -> entry[KEY_IDX], entry -> entry[VALUE_IDX]))
        );
    }

    public String getValue(String key) {
        return valuesByKey.get(key);
    }
}
