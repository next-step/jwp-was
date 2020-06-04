package http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class QueryStrings {
    private Map<String, String> queryStrings = new HashMap<>();

    public QueryStrings(Map<String, String> queryStrings) {
        this.queryStrings = Collections.unmodifiableMap(queryStrings);
    }

    public QueryStrings(String path) {
        this.queryStrings = Collections.unmodifiableMap(queryStrings);
    }

    public Map<String, String> getQueryStrings() {
        return queryStrings;
    }
}
