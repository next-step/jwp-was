package webserver.http;

import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookie cookie1 = (Cookie) o;
        return Objects.equals(cookie, cookie1.cookie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookie);
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "cookie=" + cookie +
                '}';
    }
}
