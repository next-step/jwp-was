package webserver.http.cookie;

import utils.StringUtils;

// TODO: 비슷한 기능을 하는 클래스가 너무 많다.
// TODO: 중복을 제거하자.
public class CookieValue {

    static final String SEPARATOR = "=";

    private static final int SEPARATOR_NOT_FOUND_INDEX = -1;
    private static final int START_INDEX = 0;

    private final String key;
    private final String value;

    private CookieValue(final String key,
                        final String value) {
        this.key = key;
        this.value = value;
    }

    static CookieValue of(final String rawCookieValue) {
        if (StringUtils.isBlank(rawCookieValue)) {
            throw new InvalidCookieValueException(rawCookieValue);
        }

        final int separatorIndex = parseSeparatorIndex(rawCookieValue);

        final String key = parseKey(rawCookieValue, separatorIndex);
        final String value = parseValue(rawCookieValue, separatorIndex);

        return new CookieValue(key, value);
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
            throw new InvalidCookieValueException(rawRequestQueryValue);
        }

        return separatorIndex;
    }

    private static String parseKey(final String rawRequestQueryValue,
                                   final int separatorIndex) {
        final String key = rawRequestQueryValue.substring(START_INDEX, separatorIndex);
        if (StringUtils.isBlank(key)) {
            throw new InvalidCookieValueException(rawRequestQueryValue);
        }

        return key;
    }

    private static String parseValue(final String rawRequestQueryValue,
                                     final int separatorIndex) {
        return rawRequestQueryValue.substring(separatorIndex + SEPARATOR.length());
    }
}
