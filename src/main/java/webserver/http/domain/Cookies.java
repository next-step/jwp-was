package webserver.http.domain;

import java.util.List;
import java.util.Map;

public class Cookies {
    private final Map<String, Cookie> cookies;

    public Cookies(Map<String, Cookie> cookies) {
        this.cookies = cookies;
    }

    public void addCookie(Cookie cookie) {
        cookies.put(cookie.getName(), cookie);
    }

    public List<Cookie> getCookies() {
        return List.copyOf(cookies.values());
    }
}
