package webserver.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Parts {
    public static final String QUERY_DELIMETER = "&";
    public static final String KEY_VALUE_DELIMETER = "=";
    public static final String QUERY_START = "?";

    private final String path;
    private final Map<String, String> queries;

    private Parts(String path, Map<String, String> queries) {
        this.path = path;
        this.queries = new HashMap<>(queries);
    }

    public static Parts parse(String path) {
        Map<String, String> query = new HashMap<>();

        int ind = path.lastIndexOf(QUERY_START);
        if (ind != -1) {
            query = createParams(path.substring(ind+1));
            path = path.substring(0, ind);
        }
        return new Parts(path, query);
    }

    private static Map<String, String> createParams(String queryString) {
        String[] splitedQuery = queryString.split(QUERY_DELIMETER);
        Map<String, String> queries = new HashMap<>();
        for (String query : splitedQuery) {
            String[] keyValue = query.split(KEY_VALUE_DELIMETER);
            queries.put(keyValue[0], keyValue[1]);
        }
        return queries;
    }

    public String getQuery(String key) {
        return queries.get(key);
    }

    public String getPath() {
        return path;
    }
}
