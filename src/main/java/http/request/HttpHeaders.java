package http.request;

import http.cookie.Cookie;
import http.cookie.CookieHeaderParser;
import http.session.HttpSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static http.cookie.Cookie.COOKIE_HEADER;
import static http.session.HttpSession.SESSION_HEADER_KEY;

public class HttpHeaders {

    private final Map<String, String> headers = new HashMap<>();
    private final List<Cookie> cookies = new ArrayList<>();

    public void put(String key, String value) {
        if (COOKIE_HEADER.equals(key)) {
            List<Cookie> newCookies = CookieHeaderParser.parse(value);
            this.cookies.addAll(newCookies);
            return;
        }

        headers.put(key, value);
    }

    public String findSessionId() {
        Cookie sessionCookie = cookies.stream()
                .filter(cookie -> cookie.isSameName(SESSION_HEADER_KEY))
                .findFirst()
                .orElse(null);

        return (sessionCookie != null) ? sessionCookie.getValue() : null;
    }

    public String get(String headerKey) {
        return headers.get(headerKey);
    }

    public List<Cookie> getCookies() {
        return Collections.unmodifiableList(cookies);
    }
}
