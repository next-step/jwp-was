package utils.parser;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryStringParser {
    private static final String QUERYSTRING_SEPARATOR = "?";
    private static final String FIELD_SEPARATOR = "&";
    private static final String FIELD_NAME_VALUE_SEPARATOR = "=";
    private static final int FIELD_NAME_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    public static Map<String, String> parse(String path) {
        String queryString = path.substring(path.indexOf(QUERYSTRING_SEPARATOR) + 1);
        String[] fields = queryString.split(FIELD_SEPARATOR);

        return Arrays.stream(fields).collect(
            Collectors
                .toMap(
                    filedName -> filedName.split(FIELD_NAME_VALUE_SEPARATOR)[FIELD_NAME_INDEX],
                    value -> decodeValue(value.split(FIELD_NAME_VALUE_SEPARATOR)[VALUE_INDEX])
                ));

    }

    private static String decodeValue(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    public static String removeQueryString(String path) {
        String queryString = path.substring(path.indexOf(QUERYSTRING_SEPARATOR));

        return path.replace(queryString, "");
    }
}