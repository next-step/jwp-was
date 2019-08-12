package webserver.http;

import webserver.http.util.RequestUtils;

import java.util.Map;

public class HttpCookie {
    private Map<String, String> cookies;

    public HttpCookie(String value) {
        this.cookies = RequestUtils.parseHttpCookie(value);
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }
}
