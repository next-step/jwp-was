package utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class QueryUrlParser {

    private static final String DATA_DELIMITER = "&";
    private static final String VALUE_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final String EMPTY_VALUE = "";

    private QueryUrlParser() {
        throw new IllegalArgumentException("Utils 클래스는 인스턴스화를 할 수 없습니다.");
    }

    public static Map<String, String> toMap(String query) {
        if (query == null || query.isBlank()) {
            return Collections.emptyMap();
        }
        return Stream.of(query.split(DATA_DELIMITER))
                .filter(keyValue -> keyValue.contains(VALUE_DELIMITER))
                .map(QueryUrlParser::parseToEntry)
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map.Entry<String, String> parseToEntry(String keyValue) {
        String[] splitKeyValue = URLDecoder.decode(keyValue, StandardCharsets.UTF_8).split(VALUE_DELIMITER);
        if (keyHasNoValue(splitKeyValue.length)) {
            return Map.entry(splitKeyValue[KEY_INDEX], EMPTY_VALUE);
        }
        return Map.entry(splitKeyValue[KEY_INDEX], splitKeyValue[VALUE_INDEX]);
    }

    private static boolean keyHasNoValue(int length) {
        return length == 1;
    }
}
