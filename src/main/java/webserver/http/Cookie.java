package webserver.http;

import java.util.Map;

public class Cookie {
    private final Map<String, String> cookie;

    public Cookie(Map<String, String> cookie) {
        this.cookie = cookie;
    }

    public String getCookieAsString() {
        return cookie.toString();
    }

    public Map<String, String> getCookie() {
        return cookie;
    }
}
