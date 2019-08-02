package webserver.http;

import utils.StringUtils;

class RequestQueryValue {

    private static final String EQUAL_SIGN = "=";
    private static final int SIZE = 2;

    private static final int INDEX_OF_KEY = 0;
    private static final int INDEX_OF_VALUE = 1;

    private final String key;
    private final String value;

    private RequestQueryValue(final String key,
                              final String value) {
        this.key = key;
        this.value = value;
    }

    static RequestQueryValue of(final String rawQueryStringValue) {
        if (StringUtils.isBlank(rawQueryStringValue)) {
            throw new InvalidQueryStringValueException(rawQueryStringValue);
        }

        final String[] splitQueryStringValue = rawQueryStringValue.split(EQUAL_SIGN);
        if (splitQueryStringValue.length != SIZE) {
            throw new InvalidQueryStringValueException(rawQueryStringValue);
        }

        final String key = splitQueryStringValue[INDEX_OF_KEY].trim();
        final String value = splitQueryStringValue[INDEX_OF_VALUE].trim();

        return new RequestQueryValue(key, value);
    }

    String getKey() {
        return key;
    }

    String getValue() {
        return value;
    }
}