package webserver.http;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class ResponseHeader {
    private final Map<HttpHeaders, Object> headers;
    private HttpCookie cookie;

    private ResponseHeader(Map<HttpHeaders, Object> headers) {
        this.headers = headers;
    }

    private ResponseHeader(Map<HttpHeaders, Object> headers, HttpCookie cookie) {
        this.headers = headers;
        this.cookie = cookie;
    }

    public static ResponseHeader text(int length, String path) {
        Map<HttpHeaders, Object> map = new EnumMap<HttpHeaders, Object>(HttpHeaders.class);
        map.put(HttpHeaders.CONTENT_TYPE, String.format("text/%s;charset=utf-8", fileExtension(path)));
        map.put(HttpHeaders.CONTENT_LENGTH, length);
        return new ResponseHeader(map);
    }

    public static ResponseHeader empty() {
        return new ResponseHeader(Collections.emptyMap());
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

    private static String fileExtension(String path) {
        return path.substring(path.lastIndexOf('.') + 1);
    }

    public boolean containsCookie() {
        return cookie != null;
    }

    public String getCookie() {
        return cookie.getValue();
    }

    public void addCookie(String cookie) {
        this.cookie = HttpCookie.of(cookie);
    }
}
