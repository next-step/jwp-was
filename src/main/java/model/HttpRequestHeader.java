package model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestHeader {
    private static final String DEFAULT_CONTENT_LENGTH = "0";

    private Map<String, String> headers;
    private HttpCookie httpCookie;

    private HttpRequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    private HttpRequestHeader(Map<String, String> headers, HttpCookie httpCookie) {
        this.headers = headers;
        this.httpCookie = httpCookie;
    }

    public static HttpRequestHeader of(List<String> headers) {
        HttpRequestHeader httpRequestHeader = createHttpRequestHeader(headers);
        if (hasCookie(headers)) {
            HttpCookie cookie = createCookie(headers);
            return httpRequestHeader.withCookie(cookie);
        }
        return httpRequestHeader;
    }

    private static HttpCookie createCookie(List<String> headers) {
        Map<String, String> cookieMap = headers.stream()
                .map(header -> header.split(": "))
                .filter(header -> HttpHeaders.SET_COOKIE.equals(header[0]))
                .collect(Collectors.toMap(key -> key[0], value -> value[1]));
        return HttpCookie.of(cookieMap);
    }

    private static boolean hasCookie(List<String> headers) {
        return headers.stream()
                .map(header -> header.split(": "))
                .anyMatch(header -> HttpHeaders.SET_COOKIE.equals(header[0]));
    }

    private HttpRequestHeader withCookie(HttpCookie cookie) {
        return new HttpRequestHeader(this.headers, cookie);
    }

    private static HttpRequestHeader createHttpRequestHeader(List<String> headers) {
        Map<String, String> headerMap = headers.stream()
                .map(header -> header.split(": "))
                .filter(header -> !HttpHeaders.SET_COOKIE.equals(header[0]))
                .collect(Collectors.toMap(key -> key[0], value -> value[1]));
        return new HttpRequestHeader(headerMap);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getContentLength() {
        return Integer.parseInt(headers.getOrDefault(HttpHeaders.CONTENT_LENGTH, DEFAULT_CONTENT_LENGTH));
    }

    public boolean containsLoginCookie() {
        return httpCookie != null && httpCookie.contains("logined=true");
    }

    public HttpCookie getCookie() {
        return httpCookie;
    }
}
