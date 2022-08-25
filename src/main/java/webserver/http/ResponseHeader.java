package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {
    private final Map<HttpHeaders, Object> headers;
    private HttpCookie cookie;

    private ResponseHeader(Map<HttpHeaders, Object> headers) {
        this.headers = headers;
        this.cookie = HttpCookie.empty();
    }

    private ResponseHeader(Map<HttpHeaders, Object> headers, HttpCookie cookie) {
        this.headers = headers;
        this.cookie = cookie;
    }

    public static ResponseHeader empty() {
        return new ResponseHeader(new HashMap<>());
    }

    public static ResponseHeader of(Map<HttpHeaders, Object> map) {
        return new ResponseHeader(map);
    }

    public static ResponseHeader of(Map<HttpHeaders, Object> headers, String cookie) {
        return new ResponseHeader(headers, HttpCookie.of(cookie));
    }

    public static ResponseHeader of(Map<HttpHeaders, Object> headers, HttpCookie cookie) {
        return new ResponseHeader(headers, cookie);
    }

    public Map<HttpHeaders, Object> getHeaders() {
        return headers;
    }

    public boolean containsCookie() {
        return cookie != null;
    }

    public String getCookieList() {
        return cookie.cookieList();
    }

    public void addHeader(HttpHeaders header, Object value) {
        this.headers.put(header, value);
    }

    public void addCookie(String cookieKey, String cookieValue) {
        this.cookie.add(cookieKey,cookieValue);
    }

    public HttpCookie getCookie() {
        return cookie;
    }
}
