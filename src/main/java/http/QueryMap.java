package http;

import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class QueryMap {
    private final Map<String, Object> queryMap;

    public QueryMap() {
        this.queryMap = Maps.newHashMap();
    }

    public void put(String key, Object value) {
        queryMap.put(key, value);
    }

    public Object get(String key) {
        return queryMap.get(key);
    }

    public String getString(String key) {
        return (String)queryMap.get(key);
    }

    public boolean isEmpty() {
        return queryMap.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public Set<Map.Entry<String, Object>> entrySet() {
        return queryMap.entrySet();
    }

    @Nonnull
    public static QueryMap mergeQueryMap(@Nullable QueryMap queryMap1, @Nullable QueryMap queryMap2) {
        if (queryMap1 == null && queryMap2 == null) {
            return new QueryMap();
        }

        if (queryMap1 == null) {
            return queryMap2;
        }

        if (queryMap2 == null) {
            return queryMap1;
        }

        QueryMap queryMap = new QueryMap();
        Stream.concat(queryMap1.entrySet().stream(), queryMap2.entrySet().stream())
                .forEach(e -> queryMap.put(e.getKey(), e.getValue()));
        return queryMap;
    }

    protected boolean containsKey(String key) {
        return queryMap.containsKey(key);
    }
}
