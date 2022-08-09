package webserver;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookie {
    private static final String COOKIE_DELIMITER = "=";

    private final String key;
    private final String value;
    private final String path;

    public Cookie(String key, String value, String path) {
        this.key = key;
        this.value = value;
        this.path = path;
    }

    public Cookie(String key, String value) {
        this(key, value, null);
    }

    public Cookie(String cookieHeader) {
        if (cookieHeader.isBlank()) {
            throw new IllegalArgumentException();
        }

        String[] split = cookieHeader.split(COOKIE_DELIMITER);
        key = split[0];
        value = String.join(COOKIE_DELIMITER, Arrays.stream(split).toList().subList(1, split.length));
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
