package http;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class QueryString {
    private static final String PARAMETER_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int HAS_PARAMS_SIZE = 2;
    private static final String EMPTY_VALUE = "";
    public static final String QUERY_PARAM_SYMBOL = "\\?";

    private final String path;
    private final Map<String, String> value;

    private QueryString(final String path, final Map<String, String> queryString) {
        this.path = path;
        this.value = queryString;
    }

    public static QueryString parse(final String value) {
        if (checkEmpty(value)) {
            return new QueryString(null, Collections.emptyMap());
        }

        return new QueryString(getPath(value), toMap(toParams(value)));
    }

    private static boolean checkEmpty(final String value) {
        return value == null || value.length() == 0;
    }

    private static String[] toParams(final String value) {
        return getSearch(value).split(PARAMETER_DELIMITER);
    }

    private static Map<String, String> toMap(String[] params) {
        return Arrays.stream(params)
                .map(param -> param.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toUnmodifiableMap(toKey(), toValue()));
    }

    private static String getPath(final String value) {
        String[] query = value.split(QUERY_PARAM_SYMBOL);

        if (query.length == 0) {
            return EMPTY_VALUE;
        }

        return query[0];
    }

    private static String getSearch(final String value) {
        String[] query = value.split(QUERY_PARAM_SYMBOL);

        if (query.length != HAS_PARAMS_SIZE) {
            return value;
        }

        return query[1];
    }

    private static Function<String[], String> toKey() {
        return values -> values[0];
    }

    private static Function<String[], String> toValue() {
        return values -> {
            if (values.length != 2) {
                return "";
            }
            return values[1];
        };
    }

    public String get(final String key) {
        return value.get(key);
    }

    public String getPath() {
        return path;
    }
}
