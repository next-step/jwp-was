package http.request.requestline;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class QueryStrings {
    private Map<String, String> queryStrings = new HashMap<>();

    public QueryStrings() {
    }

    public QueryStrings(Map<String, String> queryStrings) {
        this.queryStrings = Collections.unmodifiableMap(queryStrings);
    }

    public QueryStrings(String path) {
        Map<String, String> map = parseQueryStrings(getQueryStrings(path));
        this.queryStrings = Collections.unmodifiableMap(map);
    }

    public Map<String, String> getQueryStrings() {
        return queryStrings;
    }

    private String getQueryStrings(String path) {
        String[] splitPath = path.split("\\?");
        return splitPath[1];
    }

    public static Map<String, String> parseQueryStrings(String queryStrings) {
        String[] splitQueryStrings = queryStrings.split("&");
        Map<String, String> queries = new HashMap<>();
        for (int i = 0; i < splitQueryStrings.length; i++) {
            String[] split = splitQueryStrings[i].split("=");
            String key = split[0];
            String value = split[1];
            queries.put(key, value);
        }
        return queries;
    }
}
