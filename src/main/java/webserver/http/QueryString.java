package webserver.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class QueryString {

    private static final SimpleImmutableEntry<String, String> EMPTY_ENTRY = new SimpleImmutableEntry<>("", "");
    private final Map<String, String> map;
    private final String origin;

    private QueryString(String origin, Map<String, String> queryStringMap) {
        this.origin = origin;
        map = queryStringMap;
    }

    static QueryString parse(String queryString) {
        Map<String, String> map = QueryString.convertToMap(queryString);
        return new QueryString(queryString, map);
    }

    private static Map<String, String> convertToMap(String queryString) {
        String[] split = queryString.split("&");
        return Arrays.stream(split)
                .map(QueryString::parseKeyValue)
                .filter(item -> item != QueryString.EMPTY_ENTRY)
                .collect(Collectors.toMap(SimpleImmutableEntry::getKey, SimpleImmutableEntry::getValue));
    }

    private static SimpleImmutableEntry<String, String> parseKeyValue(String item) {
        if (StringUtils.isBlank(item)) {
            return QueryString.EMPTY_ENTRY;
        }

        int startIndex = item.indexOf("=");
        if (startIndex == -1) {
            return new SimpleImmutableEntry<>(item, item);
        }

        String key = item.substring(0, startIndex);
        String value = item.substring(startIndex + 1);
        return new SimpleImmutableEntry<>(key, value);
    }

    boolean containsKey(String key) {
        return map.containsKey(key);
    }

    Set<String> keys() {
        return map.keySet();
    }

    String get(String key) {
        return map.get(key);
    }

    String origin() {
        return origin;
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
        return Objects.equals(map, that.map) &&
                Objects.equals(origin, that.origin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map, origin);
    }
}
