package webserver.http;

import webserver.http.session.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static model.Constant.JSESSIONID;

public class Cookie {
    private final Map<String, Object> cookie;

    public Cookie() {
        cookie = new HashMap<>();
    }

    public Cookie(Map<String, Object> cookie) {
        this.cookie = cookie;
    }

    public void setCookie(String key, Object value) {
        cookie.put(key, value);
    }

    public String getCookie(String key) {
        return (String) cookie.getOrDefault(key, "");
    }

    public Map<String, Object> getCookie() {
        return cookie;
    }

    public String getSessionId() {
        return (String) cookie.getOrDefault(JSESSIONID, new HttpSession(new HashMap<>()).getId());
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
