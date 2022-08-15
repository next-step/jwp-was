package webserver.http;

import utils.EnumMapUtils;

import java.util.Map;

public class HttpCookie {
    private final Map<HttpHeaders, String> cookieMap;

    public HttpCookie(Map<HttpHeaders, String> cookieMap) {
        this.cookieMap = cookieMap;
    }

    public static HttpCookie of(String value) {
        return new HttpCookie(EnumMapUtils.of(Map.of(HttpHeaders.SET_COOKIE, value)));
    }

    public static HttpCookie of(Map<HttpHeaders, String> cookieMap) {
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
