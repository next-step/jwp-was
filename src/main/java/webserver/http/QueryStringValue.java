package webserver.http;

import utils.StringUtils;

class QueryStringValue {

    private static final String EQUAL_SIGN = "=";
    private static final int SIZE = 2;

    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final String key;
    private final String value;

    private QueryStringValue(final String key,
                             final String value) {
        this.key = key;
        this.value = value;
    }

    static QueryStringValue of(final String rawQueryStringValue) {
        if (StringUtils.isBlank(rawQueryStringValue)) {
            throw new InvalidQueryStringValueException(rawQueryStringValue);
        }

        final String[] splitQueryStringValue = rawQueryStringValue.split(EQUAL_SIGN);
        if (splitQueryStringValue.length != SIZE) {
            throw new InvalidQueryStringValueException(rawQueryStringValue);
        }

        final String key = splitQueryStringValue[KEY_INDEX].trim();
        final String value = splitQueryStringValue[VALUE_INDEX].trim();

        return new QueryStringValue(key, value);
    }

    String getKey() {
        return key;
    }

    String getValue() {
        return value;
    }
}