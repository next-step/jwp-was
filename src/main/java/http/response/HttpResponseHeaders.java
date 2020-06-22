package http.response;

import http.Headers;
import http.HttpCookies;

import java.util.Collection;

public class HttpResponseHeaders extends Headers {
    private final HttpCookies cookies;

    public HttpResponseHeaders() {
        this.cookies = HttpCookies.create();
    }

    public void addCookie(final String key, final String value) {
        cookies.addCookie(key, value);
    }

    public String getCookie(final String key) {
        return cookies.getValue(key);
    }

    public Collection<String> getCookieNames() {
        return cookies.getCookieNames();
    }
}
