package webserver.http;

import utils.StringUtils;

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
