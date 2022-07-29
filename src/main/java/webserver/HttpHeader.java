package webserver;

import exception.Assert;

import java.util.*;
import java.util.stream.Collectors;

public class HttpHeader {

    private final static String DELIMITER = ":";
    private final static String COOKIE = "Cookie";
    private final static String DEFAULT_VALUE = "";

    private Map<String, String> headers;

    public HttpHeader(Map<String, String> headers) {
        Assert.notNull(headers, "헤더는 null이어선 안됩니다.");
        this.headers = Collections.unmodifiableMap(headers);
    }

    public static HttpHeader empty() {
        return new HttpHeader(Collections.emptyMap());
    }

    private static Map.Entry<String, String> parseToEntry(String pair) {
        String[] splitPair = pair.split(DELIMITER, 2);
        return Map.entry(
                splitPair[0].trim(),
                splitPair[1].trim());
    }

    public static HttpHeader from(Map<String, String> header) {
        return new HttpHeader(
                Objects.requireNonNullElse(header, Collections.emptyMap())
        );
    }

    public static HttpHeader from(List<String> headers) {
        if (headers.isEmpty()) {
            return empty();
        }
        Optional<HttpHeader> optionalHttpHeader = Optional.of(from(
                headers.stream().filter(
                                header -> header.contains(DELIMITER))
                        .map(HttpHeader::parseToEntry)
                        .collect(Collectors.toUnmodifiableMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue))
        ));
        return optionalHttpHeader.orElseThrow(
                () -> new IllegalArgumentException("Http 헤더는 key-value 형태여야 합니다.")
        );
    }

    public String get(String key) {
        return headers.get(key);
    }

    public int getContentLength() {
        if (get("Content-Length") == null) {
            return 0;
        }
        return Integer.parseInt(get("Content-Length"));
    }

    public Set<Map.Entry<String, String>> entries() {
        return headers.entrySet();
    }

    public String cookie() {
        return headers.getOrDefault(COOKIE, DEFAULT_VALUE);
    }
}
