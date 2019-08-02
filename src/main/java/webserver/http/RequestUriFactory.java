package webserver.http;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestUriFactory {

    private static final String PATH_SEPARATOR = "\\?";
    private static final String QUERY_PARAM_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";

    public static RequestUri parse(String line) {
        String[] split = line.split(PATH_SEPARATOR);

        if (!hasQueryParams(split)) {
            return new RequestUri(split[0]);
        }

        return new RequestUri(split[0], toQueryParams(split[1]));
    }

    private static Map<String, String> toQueryParams(String queryString) {
        return Arrays.stream(queryString.split(QUERY_PARAM_SEPARATOR))
                .map(RequestUriFactory::getKeyValue)
                .filter(RequestUriFactory::isNotEmpty)
                .filter(RequestUriFactory::isPair)
                .collect(Collectors.toMap(keyValue -> keyValue[0], keyValue -> keyValue[1]));
    }

    private static String[] getKeyValue(String queryString) {
        return queryString.split(KEY_VALUE_SEPARATOR);
    }

    private static boolean hasQueryParams(String[] split) {
        return split.length > 1;
    }

    private static boolean isNotEmpty(String[] target) {
        return target.length != 0;
    }

    private static boolean isPair(String[] keyValue) {
        return keyValue.length == 2;
    }
}
