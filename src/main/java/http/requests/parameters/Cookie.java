package http.requests.parameters;

import java.util.Map;
import java.util.Objects;

public class Cookie {

    private final Map<String, String> cookieShop;

    public Cookie(Map<String, String> cookieShop) {
        this.cookieShop = cookieShop;
    }

    public String getValue(String key) {
        return cookieShop.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cookie cookie = (Cookie) o;
        return Objects.equals(cookieShop, cookie.cookieShop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookieShop);
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "cookieShop=" + cookieShop +
                '}';
    }
}
