package webserver.http.header;

import utils.StringUtils;
import webserver.http.HeaderName;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class HttpHeaders {

    private static final int DEFAULT_CONTENT_LENGTH = 0;

    private final Map<String, String> headers;

    private HttpHeaders(final Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpHeaders of(final String rawHttpHeaders) {
        if (StringUtils.isBlank(rawHttpHeaders)) {
            return empty();
        }

        return Arrays.stream(rawHttpHeaders.split(lineSeparator()))
                .map(HttpHeader::of)
                .collect(collectingAndThen(toMap(HttpHeader::getKey, HttpHeader::getValue),
                        HttpHeaders::new));
    }

    public static HttpHeaders empty() {
        return new HttpHeaders(new HashMap<>());
    }

    public void add(final String key,
                    final String value) {
        headers.put(key, value);
    }

    public void add(final HeaderName key,
                    final String value) {
        add(key.toString(), value);
    }

    public void add(final HeaderName key,
                    final int value) {
        add(key, String.valueOf(value));
    }

    public void setLocation(final String redirectPath) {
        add(HeaderName.LOCATION, redirectPath);
    }

    public void setContentLength(final int contentLength) {
        add(HeaderName.CONTENT_LENGTH, contentLength);
    }

    public String getString(final String key) {
        return headers.get(key);
    }

    public int getInt(final String key) {
        return Integer.parseInt(getString(key));
    }

    public int getInt(final HeaderName key,
                      final int defaultValue) {
        try {
            return getInt(key.toString());
        } catch (final NumberFormatException ignore) { }

        return defaultValue;
    }

    public int getContentLength() {
        return getInt(HeaderName.CONTENT_LENGTH, DEFAULT_CONTENT_LENGTH);
    }

    public boolean isEmpty() {
        return headers.isEmpty();
    }

    public Set<Map.Entry<String, String>> entrySet() {
        return headers.entrySet();
    }

    public List<HttpHeader> toList() {
        return entrySet().stream()
                .map(entry -> HttpHeader.of(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "HttpHeaders{" +
                "headers=" + headers +
                '}';
    }
}
