package webserver.http.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Query {

    private final Map<Key, Value> query = new HashMap<>();

    public Query() {}

    public Query(final String queryString) {
        if (StringUtils.isBlank(queryString)) {
            return;
        }

        final List<String> queries = Arrays.asList(queryString.split("&"));
        queries.forEach(query -> {
            if (!StringUtils.contains(query, '=')) {
                return;
            }

            final String[] splitQuery = query.split("=");
            final int INDEX_OF_KEY = 0;
            final int INDEX_OF_VALUE = 1;

            final String key = splitQuery[INDEX_OF_KEY];
            final String value = splitQuery[INDEX_OF_VALUE];

            this.query.put(new Key(key), new Value(value));
        });
    }

    @EqualsAndHashCode
    private static class Key {
        final String key;

        private Key(final String key) {
            this.key = key;
        }
    }

    @Getter
    @EqualsAndHashCode
    private static class Value {
        final String value;

        private Value() {
            this.value = "";
        }

        private Value(final String value) {
            this.value = value;
        }
    }

    public String get(final String key) {
        final Value value = this.query.getOrDefault(new Key(key), new Value());

        return value.getValue();
    }
}
