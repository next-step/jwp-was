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

    static RequestQueryValue of(final String rawRequestQueryValue) {
        if (StringUtils.isBlank(rawRequestQueryValue)) {
            throw new InvalidRequestQueryValueException(rawRequestQueryValue);
        }

        final String[] splitRequestQueryValue = rawRequestQueryValue.split(EQUAL_SIGN);
        if (splitRequestQueryValue.length != SIZE) {
            throw new InvalidRequestQueryValueException(rawRequestQueryValue);
        }

        final String key = splitRequestQueryValue[INDEX_OF_KEY].trim();
        final String value = splitRequestQueryValue[INDEX_OF_VALUE].trim();

        return new RequestQueryValue(key, value);
    }

    String getKey() {
        return key;
    }

    String getValue() {
        return value;
    }
}