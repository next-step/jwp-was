package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class RequestURL {
    public static final String QUERY_DELIMETER = "&";
    public static final String KEY_VALUE_DELIMETER = "=";
    public static final String QUERY_START = "?";

    private final String path;
    private final Map<String, String> queries;

    private RequestURL(String path, Map<String, String> queries) {
        this.path = path;
        this.queries = queries;
    }

    public static RequestURL parse(String path) {
        Map<String, String> query = new HashMap<>();

        int ind = path.lastIndexOf(QUERY_START);
        if (ind != -1) {
            query = createParams(path.substring(ind+1));
            path = path.substring(0, ind);
        }
        return new RequestURL(path, query);
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
