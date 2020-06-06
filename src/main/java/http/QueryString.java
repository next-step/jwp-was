package http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class QueryString {

    private final Map<String, String> queryMap;

    QueryString(Map<String, String> queryMap) {
        this.queryMap = Collections.unmodifiableMap(queryMap);
    }

    public static QueryString of(String queryString) {
        String[] queries = queryString.split("&");

        Map<String, String> queryMap = new HashMap<>();
        for (String query : queries) {
            String[] queryValue = query.split("=");
            if (queryValue.length != 2) {
                continue;
            }

            try {
                queryMap.put(URLDecoder.decode(queryValue[0], "UTF-8"),
                    URLDecoder.decode(queryValue[1]));
            } catch (UnsupportedEncodingException e) {
            }
        }

        return new QueryString(queryMap);
    }

    public String get(String name) {
        return this.queryMap.get(name);
    }

    public Map<String, String> getAll() {
        return new HashMap<>(queryMap);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QueryString that = (QueryString) o;
        return Objects.equals(queryMap, that.queryMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queryMap);
    }
}
