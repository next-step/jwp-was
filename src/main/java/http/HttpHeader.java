package http;

import exception.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.session.HttpSession;
import webserver.session.HttpSessionStorage;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class HttpHeader {
    private static final Logger logger = LoggerFactory.getLogger(HttpHeader.class);

    public static final String LOCATION = "Location";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String CONTENT_TYPE = "Content-Type";

    private static final String DELIMITER = ":";

    private Cookies cookies;
    private Map<String, String> headers;

    private HttpHeader(Map<String, String> headers) {
        Assert.notNull(headers, "헤더는 null이어선 안됩니다.");
        this.headers = Collections.unmodifiableMap(headers);
        this.cookies = Cookies.from(Collections.emptyMap());
    }

    public static HttpHeader empty() {
        return new HttpHeader(Collections.emptyMap());
    }

    private static Map.Entry<String, String> parseToEntry(String pair) {
        String[] splitPair = pair.split(DELIMITER, 2);
        logger.debug("Header: {}", splitPair[0].trim() + ": " + splitPair[1].trim());
        return Map.entry(
                splitPair[0].trim(),
                splitPair[1].trim());
    }

    public static HttpHeader from(Map<String, String> header) {
        if (header.containsKey("Cookie")) {
            Cookies.from(header.get("Cookie"));
            return new HttpHeader(Collections.emptyMap());
        }
        return new HttpHeader(Objects.requireNonNullElse(header, Collections.emptyMap()));
    }

    public static HttpHeader from(List<String> headers) {
        if (headers.isEmpty()) {
            return empty();
        }
        return from(headers.stream()
                .filter(header -> header.contains(DELIMITER))
                .map(HttpHeader::parseToEntry)
                .collect(Collectors.toUnmodifiableMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue))
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

    public String getCookieValue(String key) {
        return cookies.getCookieValue(key);
    }

    public Set<Map.Entry<String, String>> getCookies() {
        return cookies.getCookies();
    }

    public HttpHeader add(String header, String value) {
        HashMap<String, String> newHeader = new HashMap<>(headers);
        newHeader.put(header, value);
        return from(newHeader);
    }

    public HttpSession getSession() {
        return HttpSessionStorage.getSession(getCookieValue(HttpSessionStorage.JSESSIONID));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpHeader that = (HttpHeader) o;
        return Objects.equals(headers, that.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }
}
