package webserver.request;

import utils.MapUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Query {

    private final Map<String, String> queryMap;

    public Query(Map<String, String> queryMap) {
        this.queryMap = queryMap;
    }

    public static Query empty() {
        return new Query(new HashMap<>());
    }

    public static Query from(String queryStr) {
        Map<String, String> queryMap = MapUtils.parse(queryStr);
        return new Query(queryMap);
    }

    public Map<String, String> getQueryMap() {
        return new HashMap<>(queryMap);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Query)) return false;
        Query query = (Query) o;
        return Objects.equals(getQueryMap(), query.getQueryMap());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQueryMap());
    }
}
