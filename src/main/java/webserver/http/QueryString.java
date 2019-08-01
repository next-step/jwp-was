package webserver.http;

import utils.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class QueryString {

    static final QueryString EMPTY = new QueryString(Collections.emptyMap());

    private static final String EQUAL_SIGN = "=";
    private static final String AMPERSAND = "&";

    private final Map<String, String> queryString;

    private QueryString(final Map<String, String> queryString) {
        this.queryString = queryString;
    }

    public static QueryString of(final String rawQueryString) {
        if (StringUtils.isBlank(rawQueryString)) {
            return EMPTY;
        }

        return Arrays.stream(rawQueryString.split(AMPERSAND))
                .map(s -> s.split(EQUAL_SIGN))
                .map(QueryStringValue::new)
                .collect(collectingAndThen(toMap(QueryStringValue::getKey, QueryStringValue::getValue), QueryString::new));
    }

    public String getString(final String key) {
        return queryString.get(key);
    }

    private static class QueryStringValue {

        private static final int KEY_INDEX = 0;
        private static final int VALUE_INDEX = 1;

        private final String key;
        private final String value;

        private QueryStringValue(final String[] queryStrings) {
            key = queryStrings[KEY_INDEX];
            value = queryStrings[VALUE_INDEX];
        }

        private String getKey() {
            return key;
        }

        private String getValue() {
            return value;
        }
    }
}
