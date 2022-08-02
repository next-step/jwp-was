package webserver;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookie {
    private static final String HEADER_KEY = "Cookie";
    private static final String HEADER_DELIMITER = ";";
    private static final String COOKIE_DELIMITER = "=";

    private final String key;
    private final String value;
    private final String path;

    public static Map<String, Cookie> fromHeaders(HttpHeader headers) {
        String cookieHeader = headers.getHeader(HEADER_KEY);
        if (cookieHeader == null) {
            return Map.of();
        }

        return Arrays.stream(cookieHeader.replaceAll(" ", "").split(HEADER_DELIMITER))
                .map(Cookie::new)
                .collect(Collectors.toMap(Cookie::getKey, cookie -> cookie));
    }

    public Cookie(String key, String value, String path) {
        this.key = key;
        this.value = value;
        this.path = path;
    }

    public Cookie(String key, String value) {
        this(key, value, null);
    }

    public Cookie(String cookieHeader) {
        String[] split = cookieHeader.split(COOKIE_DELIMITER);
        key = split[0];
        value = split[1];
        path = null;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        if (path == null) {
            return String.format("%s=%s", key, value);
        }
        return String.format("%s=%s; Path=%s", key, value, path);
    }
}
