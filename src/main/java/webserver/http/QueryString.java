package webserver.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class QueryString {


    private final Map<String, String> map;
    private final String origin;

    private QueryString(String origin, Map<String, String> queryStringMap) {
        this.origin = origin;
        map = queryStringMap;
    }

    static QueryString parse(String queryString) {
        return QueryStringParser.parse(queryString);
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

    private static class QueryStringParser {
        private static final SimpleImmutableEntry<String, String> EMPTY_ENTRY = new SimpleImmutableEntry<>("", "");

        static QueryString parse(String queryString) {
            return new QueryString(queryString, QueryStringParser.convertToMap(queryString));
        }

        private static Map<String, String> convertToMap(String queryString) {
            String[] split = queryString.split("&");
            return Arrays.stream(split)
                    .map(QueryStringParser::parseKeyValue)
                    .filter(item -> item != QueryStringParser.EMPTY_ENTRY)
                    .collect(Collectors.toMap(SimpleImmutableEntry::getKey, SimpleImmutableEntry::getValue));
        }

        private static SimpleImmutableEntry<String, String> parseKeyValue(String item) {
            if (StringUtils.isBlank(item)) {
                return QueryStringParser.EMPTY_ENTRY;
            }

            int startIndex = item.indexOf("=");
            if (startIndex == -1) {
                return new SimpleImmutableEntry<>(item, item);
            }

            String key = item.substring(0, startIndex);
            String value = QueryStringParser.decodeValue(item.substring(startIndex + 1));
            return new SimpleImmutableEntry<>(key, value);
        }

        private static String decodeValue(String value) {
            try {
                return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException ex) {
                return value;
            }
        }
    }
}
