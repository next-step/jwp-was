package webserver.http;

import java.util.Map;

public class Cookie {
    private final Map<String, String> cookie;

    public Cookie(Map<String, String> cookie) {
        this.cookie = cookie;
    }

    public String getCookie(String key) {
        return cookie.get(key);
    }

    public Map<String, String> getCookie() {
        return cookie;
    }
}
