package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class RequestURL {
    public static final String QUERY_DELIMETER = "&";
    public static final String KEY_VALUE_DELIMETER = "=";
    public static final String QUERY_START = "?";

    private final String path;
    private final String queryString;
    private final Map<String, String> queries = new HashMap<>();

    private RequestURL(String path) {
        this.path = path;
        this.queryString = null;
    }

    private RequestURL(String path, String queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static RequestURL parse(String url) {
        int ind = url.lastIndexOf(QUERY_START);
        if (ind != -1) {
            String queryString = url.substring(ind+1);
            String path = url.substring(0, ind);
            return new RequestURL(path, queryString);
        }
        return new RequestURL(url);
    }

    public String getQueryString() { return queryString; }

    public String getQuery(String key) {
        return queries.get(key);
    }

    public String getPath() {
        return path;
    }
}
