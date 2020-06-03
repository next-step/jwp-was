package http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QueryString {

    private final Map<String, String> map;

    public QueryString(String raw) {
        final Map<String, String> map = new HashMap<>();
        Arrays.stream(raw.split("&"))
        .forEach(s -> {
            final String[] keyAndValue = s.split("=");
            map.put(keyAndValue[0], keyAndValue[1]);
        });
        this.map = map;
    }

    public String getParameter(String attributeName) {
        return map.get(attributeName);
    }
}
