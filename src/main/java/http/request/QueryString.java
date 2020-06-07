package http.request;

import lombok.Getter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Getter
public class QueryString {
    private static final String QUERY_STRING_REGEX = "&";
    private static final String KEY_VALUE_REGEX = "=";
    private static final String CHAR_SET = "UTF-8";

    private Map<String, String> queryStringMap;

    private QueryString(Map<String, String> queryStringMap) {
        this.queryStringMap = queryStringMap;
    }

    public static QueryString parse(String value) throws UnsupportedEncodingException {
        Map<String, String> queryStringMap = new HashMap<>();
        if (value.equals("")) {
            return new QueryString(queryStringMap);
        }

        value = URLDecoder.decode(value, CHAR_SET);
        String[] queryStrings = value.split(QUERY_STRING_REGEX);

        for (int i = 0; i < queryStrings.length; i++) {
            String[] values = queryStrings[i].split(KEY_VALUE_REGEX);
            queryStringMap.put(values[0], values[1]);
        }

        return new QueryString(queryStringMap);
    }

    public String getValue(String key) {
        return this.queryStringMap.get(key);
    }
}
