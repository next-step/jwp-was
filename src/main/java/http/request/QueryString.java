package http.request;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class QueryString {
    private static final String QUERY_STRING_REGEX = "&";
    private static final String KEY_VALUE_REGEX = "=";

    private Map<String, String> queryStringMap;

    private QueryString(Map<String, String> queryStringMap) {
        this.queryStringMap = queryStringMap;
    }

    public static QueryString of(String value) {
        Map<String, String> queryStringMap = new HashMap<>();

        String[] queryStrings = value.split(QUERY_STRING_REGEX);

        if (queryStrings.length < 2) {
            return new QueryString(queryStringMap);
        }

        for (int i = 0; i < queryStrings.length; i++) {
            String[] values = queryStrings[i].split(KEY_VALUE_REGEX);
            queryStringMap.put(values[0], values[1]);
        }

        return new QueryString(queryStringMap);
    }

    public Object getValue(String key) {
        return this.queryStringMap.get(key);
    }
}
