package utils;

import java.util.Map;

public class QueryParse {
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String VALUES_DELIMITER = "; ";
    private static final String AND_DELIMITER = "&";

    public static Map.Entry<String, String> parseToMap(String param) {
        String[] paramSplit = param.split(KEY_VALUE_DELIMITER);
        return Map.entry(paramSplit[0], paramSplit[1]);
    }

    public static String[] parse(String param) {
        return param.split(VALUES_DELIMITER);
    }

    public static String[] values(String param) {
        return param.split(AND_DELIMITER);
    }

}
