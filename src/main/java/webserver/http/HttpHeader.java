package webserver.http;

import utils.StringUtils;

public class HttpHeader {

    static final String SEPARATOR = ": ";

    private static final int SEPARATOR_NOT_FOUND_INDEX = -1;
    private static final int START_INDEX = 0;

    private final String key;
    private final String value;

    private HttpHeader(final String key,
                       final String value) {
        this.key = key;
        this.value = value;
    }

    static HttpHeader of(final String rawHttpHeader) {
        if (StringUtils.isBlank(rawHttpHeader)) {
            throw new InvalidHttpHeaderException(rawHttpHeader);
        }

        final int separatorIndex = parseSeparatorIndex(rawHttpHeader);

        final String key = parseKey(rawHttpHeader, separatorIndex);
        final String value = parseValue(rawHttpHeader, separatorIndex);

        return new HttpHeader(key, value);
    }

    public static HttpHeader of(final String key,
                                final String value) {
        return new HttpHeader(key, value);
    }

    String getKey() {
        return key;
    }

    String getValue() {
        return value;
    }

    private static int parseSeparatorIndex(final String rawHttpHeader) {
        final int separatorIndex = rawHttpHeader.indexOf(SEPARATOR);
        if (separatorIndex == SEPARATOR_NOT_FOUND_INDEX) {
            throw new InvalidHttpHeaderException(rawHttpHeader);
        }

        return separatorIndex;
    }

    private static String parseKey(final String rawHttpHeader,
                                   final int separatorIndex) {
        final String key = rawHttpHeader.substring(START_INDEX, separatorIndex);
        if (StringUtils.isBlank(key)) {
            throw new InvalidRequestQueryValueException(rawHttpHeader);
        }

        return key;
    }

    private static String parseValue(final String rawHttpHeader,
                                     final int separatorIndex) {
        return rawHttpHeader.substring(separatorIndex + SEPARATOR.length());
    }

    @Override
    public String toString() {
        return key + SEPARATOR + value;
    }
}
