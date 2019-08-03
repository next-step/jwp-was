package webserver.http;

import utils.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class HttpHeaders {

    public static final HttpHeaders EMPTY = new HttpHeaders(Collections.emptyMap());

    private final Map<String, String> headers;

    private HttpHeaders(final Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpHeaders of(final String rawHttpHeaders) {
        if (StringUtils.isBlank(rawHttpHeaders)) {
            return EMPTY;
        }

        return Arrays.stream(rawHttpHeaders.split(lineSeparator()))
                .map(HttpHeader::of)
                .collect(collectingAndThen(toMap(HttpHeader::getKey, HttpHeader::getValue),
                        HttpHeaders::new));
    }

    public String getString(final String key) {
        return headers.get(key);
    }

    public int getInt(final String key) {
        return Integer.parseInt(getString(key));
    }

    public int getInt(final String key,
                      final int defaultValue) {
        try {
            return getInt(key);
        } catch (final NumberFormatException ignore) { }

        return defaultValue;
    }

    public int getContentLength() {
        return getInt("Content-Length", 0);
    }

    @Override
    public String toString() {
        return "HttpHeaders{" +
                "headers=" + headers +
                '}';
    }
}
