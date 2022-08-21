package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Cookie {
    public static final String REQUEST_COOKIE_HEADER = "Cookie";
    public static final String RESPONSE_COOKIE_HEADER = "Set-Cookie";
    private static final String COOKIE_DELIMITER = "; ";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String PATH_KEY = "Path";

    private final Map<String, String> values = new HashMap<>();

    private Cookie() {

    }

    public static Cookie from(String values) {
        Cookie cookie = new Cookie();
        if (Objects.isNull(values) || values.isEmpty()) {
            return cookie;
        }
        Arrays.stream(values.split(COOKIE_DELIMITER))
                .map(value -> value.split(KEY_VALUE_DELIMITER))
                .forEach(value -> cookie.put(value[0], value[1]));

        return cookie;
    }

    public void put(String key, String value) {
        values.put(key, value);
    }

    public String getResponseCookie() {
        List<String> valuesOfCookie = values.entrySet().stream()
                .filter(entry -> !PATH_KEY.equals(entry.getKey()))
                .map(entry -> entry.getKey() + KEY_VALUE_DELIMITER + entry.getValue())
                .collect(Collectors.toList());

        return String.join(COOKIE_DELIMITER, valuesOfCookie) + COOKIE_DELIMITER + PATH_KEY + KEY_VALUE_DELIMITER + "/";
    }

    public String get(String key) {
        return values.get(key);
    }
}
