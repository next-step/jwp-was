package utils;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class StringParseUtils {
    private static final int MIN_QUERY_VALUE_LENGTH = 2;
    private static final String DELIMITER_OF_COOKIE = ";";
    private static final String DELIMITER_OF_PATH_PARAMS = "&";
    private static final String DELIMITER_OF_KEY_AND_VALUE = "=";

    public static Map<String, String> parseRequestParam(final String values) {
        return parseValues(values, DELIMITER_OF_PATH_PARAMS);
    }

    public static Map<String, String> parseCookies(final String values) {
        return parseValues(values, DELIMITER_OF_COOKIE);
    }

    private static Map<String, String> parseValues(final String values, final String separator) {
        final Map<String, String > parseValueMap = Maps.newHashMap();
        if (Strings.isNullOrEmpty(values)) {
            return parseValueMap;
        }

        return Arrays.stream(values.split(separator))
                .map(keyAndValue -> keyAndValue.trim().split(DELIMITER_OF_KEY_AND_VALUE))
                .filter(value -> value.length == MIN_QUERY_VALUE_LENGTH)
                .collect(Collectors.toMap(value -> value[0], value -> value[1]));
    }
}
