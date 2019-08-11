package webserver.http.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestUtils {
    private static final String QUERY_STRING_DELIMITER = "&";
    private static final String COOKIE_DELIMITER = "; ";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int KEY_VALUE_PAIR_COUNT = 2;


    public static Map parseHttpCookie(String value) {
        if (value == null)
            return Collections.EMPTY_MAP;

        return parse(value, COOKIE_DELIMITER);
    }

    public static Map parseQueryParameter(String value) {
        if (value.isEmpty()) {
            return Collections.EMPTY_MAP;
        }

        return parse(value, QUERY_STRING_DELIMITER);
    }

    private static Map parse(String value, String delimiter) {
        String [] keyValues = value.split(delimiter);

        return Arrays.stream(keyValues)
                .map(keyValue -> keyValue.split(KEY_VALUE_DELIMITER))
                .filter(RequestUtils::hasKeyValue)
                .collect(Collectors.toMap(values -> values[0], values -> values[1], (a, b) -> b));
    }

    private static boolean hasKeyValue(String[] values) {
        return values.length == KEY_VALUE_PAIR_COUNT;
    }
}
