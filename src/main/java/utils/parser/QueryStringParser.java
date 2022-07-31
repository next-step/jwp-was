package utils.parser;

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

        return Arrays.stream(fields)
            .collect(Collectors
                .toMap(
                    filedName -> filedName.split(FIELD_NAME_VALUE_SEPARATOR, 2)[FIELD_NAME_INDEX],
                    value -> value.split(FIELD_NAME_VALUE_SEPARATOR, 2)[VALUE_INDEX]
                ));

    }

    public static String removeQueryString(String path) {
        String queryString = path.substring(path.indexOf(QUERYSTRING_SEPARATOR));

        return path.replace(queryString, "");
    }
}