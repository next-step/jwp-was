package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cookie {

    private static final String COOKIE_VALUES_SEPARATOR = ";";
    private static final String COOKIE_KEY_VALUE_SEPARATOR = "=";
    private final Map<String, String> storage;

    public Cookie(String values) {
        this.storage = new HashMap<>();
        Arrays.stream(values.split(COOKIE_VALUES_SEPARATOR))
                .map(String::trim)
                .forEach(value -> {
                    String cookieKey = value.split(COOKIE_KEY_VALUE_SEPARATOR)[0];
                    String cookieValue = value.split(COOKIE_KEY_VALUE_SEPARATOR)[1];
                    this.storage.put(cookieKey, cookieValue);
                });
    }

    public String getValue(String key) {
        return this.storage.get(key);
    }

}
