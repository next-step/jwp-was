package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static model.Constant.JSESSIONID;

public class Cookie {
    private Map<String, Object> cookie = new HashMap<>();

    public void setCookie(String key, Object value) {
        cookie.put(key, value);
    }

    public String getSessionId() {
        if (!cookie.containsKey(JSESSIONID)) {
            return "";
        }
        return (String) cookie.get(JSESSIONID);
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
