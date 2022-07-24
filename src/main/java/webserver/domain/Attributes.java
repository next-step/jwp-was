package webserver.domain;

import java.util.HashMap;
import java.util.Map;

public class Attributes {
    public static final String TEMPLATE_KEY = "template";
    private final Map<String, String> store = new HashMap<>();

    public void addAttribute(String key, String value) {
        store.put(key, value);
    }

    public String get(String key) {
        if (!store.containsKey(key)) {
            throw new IllegalArgumentException();
        }

        return store.get(key);
    }

    public String getTemplate() {
        return get(TEMPLATE_KEY);
    }
}
