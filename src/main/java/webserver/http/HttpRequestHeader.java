package webserver.http;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HttpRequestHeader {
    private static final String DEFAULT_CONTENT_LENGTH = "0";

    private Map<HttpHeaders, String> headers;
    private HttpCookie httpCookie;

    private HttpRequestHeader(Map<HttpHeaders, String> headers) {
        this.headers = headers;
    }

    private HttpRequestHeader(Map<HttpHeaders, String> headers, HttpCookie httpCookie) {
        this.headers = headers;
        this.httpCookie = httpCookie;
    }

    public static HttpRequestHeader of(List<String> headers) {
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(createHeaderMap(headers, equalsCookie().negate()));
        if (hasCookie(headers)) {
            HttpCookie cookie = HttpCookie.of(createHeaderMap(headers, equalsCookie()));
            return httpRequestHeader.withCookie(cookie);
        }
        return httpRequestHeader;
    }

    private static Predicate<String[]> equalsCookie() {
        return (header) -> HttpHeaders.SET_COOKIE.equals(header[0]);
    }

    private static Map<HttpHeaders, String> createHeaderMap(List<String> headers, Predicate<String[]> equalsCookie) {
        return headers.stream()
                .map(header -> header.split(": "))
                .filter(equalsCookie)
                .collect(Collectors.toMap(key -> HttpHeaders.of(key[0]), value -> value[1]));
    }

    private static boolean hasCookie(List<String> headers) {
        return headers.stream()
                .map(header -> header.split(": "))
                .anyMatch(header -> HttpHeaders.SET_COOKIE.equals(header[0]));
    }

    private HttpRequestHeader withCookie(HttpCookie cookie) {
        return new HttpRequestHeader(this.headers, cookie);
    }

    public Map<HttpHeaders, String> getHeaders() {
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
