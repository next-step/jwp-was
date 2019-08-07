package utils;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static utils.StringUtils.endSplit;
import static utils.StringUtils.frontSplitWithOrigin;

public class ParsingUtils {

    private static final String URL_FIELD_DELIMITER = "&";
    private static final String COOKIE_FIELD_DELIMITER = ";";
    private static final char KEY_VALUE_DELIMITER = '=';

    public static Map<String, String> parseUrl(String query) {
        return parse(query.split(URL_FIELD_DELIMITER));
    }

    public static Map<String, String> parseCookie(String query) {
        return parse(query.split(COOKIE_FIELD_DELIMITER));
    }

    public static Map<String, String> parse(String[] parameters) {
        if (parameters.length == 0) {
            return emptyMap();
        }

        Map<String, String> queries = new HashMap<>(parameters.length);
        for (String parameter : parameters) {
            String key = frontSplitWithOrigin(parameter, KEY_VALUE_DELIMITER);
            MapUtils.putIfKeyNotBlank(queries, key, endSplit(parameter, KEY_VALUE_DELIMITER));
        }
        return queries;
    }


}
