package webserver.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookie {
    public static final String KEY_VALUE_DELIMITER = "=";
    public static final String ATTRIBUTE_DELIMITER = "; ";
    private final Map<String, String> store = new HashMap<>();

    public Cookie() {
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Cookie(@JsonProperty(value = "store") Map<String, String> store) {
        this.store.putAll(store);
    }

    public static Cookie from(String line) {
        Cookie cookie = new Cookie();
        if (line == null) {
            return cookie;
        }
        String[] values = line.split(ATTRIBUTE_DELIMITER);
        for (String value : values) {
            String[] keyValue = value.split(KEY_VALUE_DELIMITER);
            cookie.addAttribute(keyValue[0], keyValue[1]);
        }

        return cookie;
    }

    public void addAttribute(String key, String value) {
        store.put(key, value);
    }

    @Override
    public String toString() {
        return store.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("; "));
    }

    public String getAttribute(String key) {
        return store.getOrDefault(key, null);
    }

    public Map<String, String> getStore() {
        return Collections.unmodifiableMap(store);
    }
}
