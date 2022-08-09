package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {
    private final Map<String, Object> headers;
    private HttpCookie cookie;

    private ResponseHeader(Map<String, Object> headers) {
        this.headers = headers;
    }

    private ResponseHeader(Map<String, Object> headers, HttpCookie cookie) {
        this.headers = headers;
        this.cookie = cookie;
    }

    public static ResponseHeader text(int length, String path) {
        Map<String, Object> map = new HashMap<>();
        map.put(HttpHeaders.CONTENT_TYPE, String.format("text/%s;charset=utf-8", fileExtension(path)));
        map.put(HttpHeaders.CONTENT_LENGTH, length);
        return new ResponseHeader(map);
    }

    public static ResponseHeader empty() {
        return new ResponseHeader(Collections.emptyMap());
    }

    public static ResponseHeader of(Map<String, Object> map) {
        return new ResponseHeader(map);
    }

    public static ResponseHeader of(Map<String, Object> headers, String cookie) {
        return new ResponseHeader(headers, HttpCookie.of(cookie));
    }

    public static ResponseHeader of(Map<String, Object> headers, HttpCookie cookie) {
        return new ResponseHeader(headers, cookie);
    }

    public Map<String, Object> getHeaders() {
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
}
