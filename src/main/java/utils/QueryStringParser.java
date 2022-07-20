package utils;

import java.util.HashMap;
import java.util.Map;

public class QueryStringParser {
    private static final String QUERYSTRING_SEPARATOR = "?";

    public static Map<String, String> parse(String path) {
        String queryString = path.substring(path.indexOf(QUERYSTRING_SEPARATOR) + 1);

        HashMap<String, String> parsedQueryString = new HashMap<>();
        parsedQueryString.put("queryString", queryString);

        return parsedQueryString;
    }
}
