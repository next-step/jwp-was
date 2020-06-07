package http;

import java.util.HashMap;
import java.util.Map;

public class Cookies {
    private final Map<String, String> cookies = new HashMap<>();

    private Cookies() {}

    public static Cookies init() {
        return new Cookies();
    }

    public void setCookie(final String key, final String value) {
        cookies.put(key, value);
    }

    public String getCookie(final String key) {
        return cookies.get(key);
    }

    public Map<String, String> getCookies() {
        return cookies;
    }
}
