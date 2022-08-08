package model;

import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cookie {

    public static final String COOKIE_KEY_VALUE_SEPARATOR = "=";
    private static final String COOKIE_VALUES_SEPARATOR = ";";
    private final Map<String, String> storage;

    public Cookie(String values) {
        this.storage = new HashMap<>();
        if ((!values.contains(COOKIE_KEY_VALUE_SEPARATOR)) || Strings.isEmpty(values)) {
            throw new IllegalArgumentException();
        }

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
