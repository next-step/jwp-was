package model;

import java.util.Map;

public class HttpCookie {
    private final Map<String, String> cookieMap;

    public HttpCookie(Map<String, String> cookieMap) {
        this.cookieMap = cookieMap;
    }

    public static HttpCookie of(String value) {
        return new HttpCookie(Map.of(HttpHeaders.SET_COOKIE, value));
    }

    public static HttpCookie of(Map<String, String> cookieMap) {
        return new HttpCookie(cookieMap);
    }

    public boolean contains(String value) {
        return cookieMap.values()
                .stream()
                .anyMatch(cookie -> cookie.contains(value));
    }

    public String getValue() {
        return String.join(",", cookieMap.values());
    }
}
