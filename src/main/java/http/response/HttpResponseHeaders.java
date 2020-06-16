package http.response;

import com.google.common.collect.Maps;
import http.HttpCookies;

import java.util.Collection;
import java.util.Map;

public class HttpResponseHeaders {
    private final Map<String, String> headers;
    private final HttpCookies cookies;

    public HttpResponseHeaders() {
        this.headers = Maps.newHashMap();
        this.cookies = HttpCookies.create();
    }

    public void addCookie(final String key, final String value) {
        cookies.addCookie(key, value);
    }

    public void addHeader(final String key, final String value) {
        headers.put(key, value);
    }

    public String getHeader(final String key) {
        return headers.getOrDefault(key, null);
    }

    public String getCookie(final String key) {
        return cookies.getValue(key);
    }

    public Collection<String> getHeaderNames() {
        return headers.keySet();
    }

    public Collection<String> getCookieNames() {
        return cookies.getCookieNames();
    }
}
