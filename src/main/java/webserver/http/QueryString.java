package webserver.http;

import org.apache.logging.log4j.util.Strings;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryString {

    private static final String DELIMITER_AMPERSAND = "&";
    private static final String DELIMITER_EQUALS = "=";
    private static final int INDEX_OF_KEY = 0;
    private static final int INDEX_OF_VALUE = 1;

    private final String original;
    private final Map<String, String> queryString;

    public QueryString(final String queryString) {
        this.original = queryString;
        this.queryString = parseQueryString(queryString);
    }

    private Map<String, String> parseQueryString(final String queryString) {
        if (Strings.isBlank(queryString)) {
            return Map.of();
        }
        return Stream.of(queryString.split(DELIMITER_AMPERSAND))
                .map(splitQueryString -> splitQueryString.split(DELIMITER_EQUALS))
                .collect(Collectors.toMap(key -> key[INDEX_OF_KEY], value -> value[INDEX_OF_VALUE]));
    }

    public String get(final String key) {
        return queryString.getOrDefault(key, "");
    }

    public String getQueryString() {
        return original;
    }
}
