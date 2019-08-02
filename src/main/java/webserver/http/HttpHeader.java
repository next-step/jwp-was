package webserver.http;

import utils.StringUtils;

class HttpHeader {

    static final String SEPARATOR = ": ";
    static final int INDEX_OF_KEY = 0;
    static final int INDEX_OF_VALUE = 1;
    private static final int SIZE = 2;

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

        final String[] splitRawHttpHeader = rawHttpHeader.split(SEPARATOR);
        if (splitRawHttpHeader.length != SIZE) {
            throw new InvalidHttpHeaderException(rawHttpHeader);
        }

        final String key = splitRawHttpHeader[INDEX_OF_KEY];
        final String value = splitRawHttpHeader[INDEX_OF_VALUE];

        return new HttpHeader(key, value);
    }

    String getKey() {
        return key;
    }

    String getValue() {
        return value;
    }
}
