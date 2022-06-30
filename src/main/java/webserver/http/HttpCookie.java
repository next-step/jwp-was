package webserver.http;

import utils.HttpRequestUtils;

import java.util.Map;

public class HttpCookie {
    private final Map<String, String> cookies;

    HttpCookie(String cookieValue) {
        cookies = HttpRequestUtils.parseCookies(cookieValue);
    }

    public String getCookie(String name) {
        return cookies.get(name);
    }
}
