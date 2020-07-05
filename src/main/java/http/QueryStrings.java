package http;

import java.util.*;
import java.util.stream.Stream;

import static org.springframework.util.StringUtils.collectionToCommaDelimitedString;

public class QueryStrings {

    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private static final String AMPERSAND = "&";
    private static final String EQUALS = "=";
    private static final String COMMA = ",";

    private final Map<String, List<String>> queryStrings;

    public QueryStrings(final String querySources) {
        this.queryStrings = new HashMap<>();
        buildQueryStrings(querySources);
    }

    public QueryStrings() {
        this.queryStrings = new HashMap<>();
    }

    public String getValue(final String key) {
        return collectionToCommaDelimitedString(queryStrings.get(key));
    }

    public void buildQueryStrings(final String values) {
        if (values.contains(AMPERSAND)) {
            String[] tokens = values.split(AMPERSAND);
            Stream.of(tokens).forEach(this::putValue);
            return;
        }
        putValue(values);
    }

    public void putValue(final String values) {
        if (values.contains(EQUALS)) {
            String[] tokens = values.split(EQUALS);
            putValue(tokens[KEY_INDEX], Arrays.asList(tokens[VALUE_INDEX].split(COMMA)));
        }
    }

    public void putValue(final String key, final List<String> values) {
        List<String> existingKey = queryStrings.getOrDefault(key, Collections.emptyList());
        values.addAll(existingKey);
        queryStrings.put(key, values);
    }

    @Override
    public String toString() {
        return "QueryStrings{" +
                "queryStrings=" + queryStrings +
                '}';
    }
}
