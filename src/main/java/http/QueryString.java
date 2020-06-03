package http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QueryString {
    private Map<String, String> params = new HashMap<>();

    public QueryString(String queryString) {
        String[] queryStrings = queryString.split("&");
        Arrays.stream(queryStrings)
                .forEach(queryString1 -> {
                    String[] values = queryString1.split("=");
                    String key = values[0];
                    String value = values[1];
                    params.put(key, value);
                });
    }

    public String getValue(String key) {
        return params.get(key);
    }
}
