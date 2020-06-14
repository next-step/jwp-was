package http.request;

import http.Cookie;
import http.Cookies;

import java.util.List;
import java.util.Map;

public class RequestHeader {

    private static final String SESSION_ID = "SessionId";
    private Map<String, String> header;
    private Cookies cookies;

    public RequestHeader(Map<String, String> header) {
        this.header = header;
        this.cookies = new Cookies(header.getOrDefault("Cookie", ""));
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public List<Cookie> getCookies() {
        return cookies.getCookies();
    }

    public String getCookie(String key) {
        return cookies.getCookie(key);
    }

    public String getSession() {
        return getCookie(SESSION_ID);
    }
}
