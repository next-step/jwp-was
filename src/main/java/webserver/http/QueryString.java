package webserver.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.util.*;

public class QueryString {

    private final Map<String, String> map;
    private final String origin;

    public static QueryString parse(String queryString) {
        Map<String, String> map = convertToMap(queryString);
        return new QueryString(queryString, map);
    }

    private static Map<String, String> convertToMap(String queryString) {
        Map<String, String> map = new HashMap<>();
        String[] split = queryString.split("&");
        for (String item : split) {
            if(StringUtils.isBlank(item)) {
                continue;
            }

            int startIndex = item.indexOf("=");
            if(startIndex == -1) {
                map.put(item, item);
                continue;
            }
            String key = item.substring(0, startIndex);
            String value = item.substring(startIndex + 1);
            map.put(key, value);
        }
        return map;
    }

    private QueryString(String origin, Map<String, String> queryStringMap) {
        this.origin = origin;
        this.map = queryStringMap;
    }

    public boolean containsKey(String key) {
        return this.map.containsKey(key);
    }

    public Set<String> keys() {
        return map.keySet();
    }

    public String get(String key) {
        return this.map.get(key);
    }

    public String origin() {
        return origin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryString that = (QueryString) o;
        return Objects.equals(map, that.map) &&
                Objects.equals(origin, that.origin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map, origin);
    }
}
