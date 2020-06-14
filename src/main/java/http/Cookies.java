package http;

import java.util.ArrayList;
import java.util.List;

public class Cookies {
    private static final String SESSION_ID = "SessionId";
    private List<Cookie> cookies = new ArrayList<>();

    public Cookies() {
    }

    public Cookies(String values) {
        String[] cookies = values.split(";");
        for (String result : cookies) {
            String[] results = result.split("=");
            addCookie(results);
        }
    }

    private void addCookie(String[] results) {
        if (results.length > 1) {
            this.cookies.add(new Cookie(results[0].trim(), results[1].trim()));
        }
    }

    public void addCookie(Cookie cookie) {
        this.cookies.add(cookie);
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public String getCookie(String key) {
        return cookies.stream()
                .filter(c -> c.getKey().equals(key))
                .findAny()
                .map(Cookie::getValue)
                .orElse(null);
    }

    public void addSession(HttpSession httpSession) {
        this.cookies.add(new Cookie(SESSION_ID, httpSession.getId()));
    }
}
