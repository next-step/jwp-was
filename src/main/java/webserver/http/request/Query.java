package webserver.http.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

@ToString
public class Query {

    private static final String AMPERSAND = "&";
    private static final String EQUALS = "=";

    private final Map<Key, Value> query = new HashMap<>();

    public Query(final String queryString) {
        if (StringUtils.isBlank(queryString)) {
            return;
        }

        String[] splitQueryString = split(queryString, AMPERSAND);
        if (splitQueryString == null) {
            return;
        }

        final List<String> queries = Arrays.asList(splitQueryString);
        setQuery(queries);
    }

    @EqualsAndHashCode
    private static class Key {
        private final String key;

        private Key(final String key) {
            this.key = key;
        }
    }

    @Getter
    @EqualsAndHashCode
    private static class Value {
        private static final Value EMPTY = new Value("");

        private final String value;

        private Value(final String value) {
            this.value = value;
        }
    }

    public String get(final String key) {
        final Value value = this.query.getOrDefault(new Key(key), Value.EMPTY);

        return value.getValue();
    }

    public boolean isEmpty() {
        return query.isEmpty();
    }

    private void setQuery(List<String> queries) {
        queries.forEach(query -> {
            final String[] splitQuery = split(query, EQUALS);
            if (splitQuery == null) {
                return;
            }

            final Key key = parseKey(splitQuery);
            final Value value = parseValue(splitQuery);

            this.query.put(key, value);
        });
    }

    private String[] split(final String str, final String delimiter) {
        if (!StringUtils.contains(str, delimiter)) {
            return null;
        }

        return str.split(delimiter);
    }

    private Key parseKey(final String[] splitQuery) {
        final int INDEX_OF_KEY = 0;
        final String key = splitQuery[INDEX_OF_KEY];

        return new Key(key);
    }

    private Value parseValue(final String[] splitQuery) {
        final int INDEX_OF_VALUE = 1;
        final String value = splitQuery[INDEX_OF_VALUE];

        return new Value(value);
    }
}
