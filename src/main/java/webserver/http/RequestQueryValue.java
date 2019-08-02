package webserver.http;

import utils.StringUtils;

class RequestQueryValue {

    private static final String SEPARATOR = "=";

    private static final int SEPARATOR_NOT_FOUND_INDEX = -1;
    private static final int START_INDEX = 0;

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

        final int separatorIndex = parseSeparatorIndex(rawRequestQueryValue);

        final String key = parseKey(rawRequestQueryValue, separatorIndex);
        final String value = parseValue(rawRequestQueryValue, separatorIndex);

        return new RequestQueryValue(key, value);
    }

    String getKey() {
        return key;
    }

    String getValue() {
        return value;
    }

    private static int parseSeparatorIndex(final String rawRequestQueryValue) {
        final int separatorIndex = rawRequestQueryValue.indexOf(SEPARATOR);
        if (separatorIndex == SEPARATOR_NOT_FOUND_INDEX) {
            throw new InvalidRequestQueryValueException(rawRequestQueryValue);
        }

        return separatorIndex;
    }

    private static String parseKey(final String rawRequestQueryValue,
                                   final int separatorIndex) {
        final String key = rawRequestQueryValue.substring(START_INDEX, separatorIndex);
        if (StringUtils.isBlank(key)) {
            throw new InvalidRequestQueryValueException(rawRequestQueryValue);
        }

        return key;
    }

    private static String parseValue(final String rawRequestQueryValue,
                                     final int separatorIndex) {
        return rawRequestQueryValue.substring(separatorIndex + SEPARATOR.length());
    }
}