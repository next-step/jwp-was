package utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class UrlQueryUtils {

    private static final String PROPERTY_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    private UrlQueryUtils() {
        throw new AssertionError("'UrlUtils' can not be instanced");
    }

    public static Map<String, String> toMap(String query) {
        if (query == null || query.isBlank()) {
            return Collections.emptyMap();
        }
        return Stream.of(query.split(PROPERTY_DELIMITER))
                .filter(keyValue -> keyValue.contains(KEY_VALUE_DELIMITER))
                .map(UrlQueryUtils::parseToEntry)
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map.Entry<String, String> parseToEntry(String keyValue) {
        String[] splitKeyValue = URLDecoder.decode(keyValue, StandardCharsets.UTF_8).split(KEY_VALUE_DELIMITER);
        if (splitKeyValue.length == 1) {
            return Map.entry(splitKeyValue[0], "");
        }
        return Map.entry(splitKeyValue[0], splitKeyValue[1]);
    }
}
