package http;

import com.google.common.collect.Maps;

import java.util.Map;

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

    protected boolean containsKey(String key) {
        return queryMap.containsKey(key);
    }
}
