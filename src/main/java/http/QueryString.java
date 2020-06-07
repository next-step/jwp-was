package http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import utils.HttpUtils;

public class QueryString {

    private final Map<String, String> queryMap;

    QueryString(Map<String, String> queryMap) {
        this.queryMap = Collections.unmodifiableMap(queryMap);
    }

    public static QueryString of(String queryString) {
        return new QueryString(HttpUtils.getPairs(queryString,"&", "="));
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
