package webserver.http.header;

import java.util.HashMap;
import java.util.Map;

public class Cookie {
    private static final String EMPTY_STRING = "";

    private Map<String, String> cookies;

    public Cookie(Map<String, String> cookies) {
        validateCookies(cookies);
        this.cookies = cookies;
    }

    private void validateCookies(Map<String, String> cookies) {
        if (cookies == null) {
            throw new IllegalArgumentException("cookies 값이 null 일 수 없습니다.");
        }
    }

    public Cookie() {
        this(new HashMap<>());
    }

    public String getValue(String key) {
        return this.cookies.getOrDefault(key, EMPTY_STRING);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cookie cookie = (Cookie) o;

        return cookies.equals(cookie.cookies);
    }

    @Override
    public int hashCode() {
        return cookies.hashCode();
    }
}
